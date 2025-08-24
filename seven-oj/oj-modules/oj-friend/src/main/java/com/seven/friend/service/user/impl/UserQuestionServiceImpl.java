package com.seven.friend.service.user.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.seven.api.domain.UserExeResult;
import com.seven.common.core.constants.Constants;
import com.seven.common.core.domain.R;
import com.seven.common.core.enums.ProgramType;
import com.seven.common.core.enums.QuestionResType;
import com.seven.common.core.enums.ResultCode;
import com.seven.common.core.utils.ThreadLocalUtil;
import com.seven.common.security.exception.ServiceException;
import com.seven.friend.domain.question.Question;
import com.seven.friend.domain.question.QuestionCase;
import com.seven.friend.domain.question.es.QuestionES;
import com.seven.friend.domain.user.UserSubmit;
import com.seven.friend.domain.user.dto.UserSubmitDTO;
import com.seven.friend.elasticsearch.QuestionRepository;
import com.seven.friend.mapper.question.QuestionMapper;
import com.seven.friend.mapper.user.UserSubmitMapper;
import com.seven.friend.rabbitmq.JudgeProducer;
import com.seven.friend.service.user.IUserQuestionService;
import com.seven.api.RemoteJudgeService;
import com.seven.api.domain.dto.JudgeSubmitDTO;
import com.seven.api.domain.vo.UserQuestionResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserQuestionServiceImpl implements IUserQuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserSubmitMapper userSubmitMapper;

    @Autowired
    private RemoteJudgeService remoteJudgeService;

    @Autowired
    private JudgeProducer judgeProducer;

    @Override
    public R<UserQuestionResultVO> submit(UserSubmitDTO userSubmitDTO) {
        Integer programType = userSubmitDTO.getProgramType();
        if(ProgramType.JAVA.getValue().equals(programType)) {
            JudgeSubmitDTO judgeSubmitDTO = assembleJudgeSubmitDTO(userSubmitDTO);
            return remoteJudgeService.doJudgeJavaCode(judgeSubmitDTO);
        }
        throw new ServiceException(ResultCode.FAILED_NOT_SUPPORT_PROGRAM);
    }

    @Override
    public boolean rabbitSubmit(UserSubmitDTO userSubmitDTO) {
        Integer programType = userSubmitDTO.getProgramType();
        if(ProgramType.JAVA.getValue().equals(programType)) {
            JudgeSubmitDTO judgeSubmitDTO = assembleJudgeSubmitDTO(userSubmitDTO);
            judgeProducer.produceMsg(judgeSubmitDTO);
            return true;
        }
        throw new ServiceException(ResultCode.FAILED_NOT_SUPPORT_PROGRAM);
    }

    @Override
    public UserQuestionResultVO exeResult(Long examId, Long questionId, String currentTime) {
        Long userId = ThreadLocalUtil.get(Constants.USER_ID, Long.class);
        UserSubmit userSubmit = userSubmitMapper.selectCurrentUserSubmit(userId, examId, questionId, currentTime);
        UserQuestionResultVO resultVO = new UserQuestionResultVO();
        if(userSubmit == null) {
            resultVO.setPass(QuestionResType.IN_JUDGE.getValue());
        } else {
            resultVO.setPass(userSubmit.getPass());
            resultVO.setExeMessage(userSubmit.getExeMessage());
            if(StrUtil.isNotEmpty(userSubmit.getCaseJudgeRes())) {
                resultVO.setUserExeResultList(JSON.parseArray(userSubmit.getCaseJudgeRes(), UserExeResult.class));
            }
        }
        return resultVO;
    }

    private JudgeSubmitDTO assembleJudgeSubmitDTO(UserSubmitDTO userSubmitDTO) {
        Long questionId = userSubmitDTO.getQuestionId();
        QuestionES questionES = questionRepository.findById(questionId).orElse(null);
        JudgeSubmitDTO judgeSubmitDTO = new JudgeSubmitDTO();
        if(questionES != null) {
            BeanUtil.copyProperties(questionES, judgeSubmitDTO);
        } else {
            Question question = questionMapper.selectById(questionId);
            BeanUtil.copyProperties(question, judgeSubmitDTO);
            questionES = new QuestionES();
            BeanUtil.copyProperties(question, questionES);
            questionRepository.save(questionES);
        }
        judgeSubmitDTO.setUserId(ThreadLocalUtil.get(Constants.USER_ID, Long.class));
        judgeSubmitDTO.setExamId(userSubmitDTO.getExamId());
        judgeSubmitDTO.setProgramType(userSubmitDTO.getProgramType());
        judgeSubmitDTO.setUserCode(codeConnect(userSubmitDTO.getUserCode(),questionES.getMainFunc()));
        List<QuestionCase> questionCaseList = JSONUtil.toList(questionES.getQuestionCase(), QuestionCase.class);
        List<String> inputList = questionCaseList.stream().map(QuestionCase::getInput).toList();
        judgeSubmitDTO.setInputList(inputList);
        List<String> outputList = questionCaseList.stream().map(QuestionCase::getOutput).toList();
        judgeSubmitDTO.setOutputList(outputList);
        return judgeSubmitDTO;
    }

    private String codeConnect(String userCode, String mainFunc) {
        String targetCharacter = "}";
        int targetLastIndex = userCode.lastIndexOf(targetCharacter);
        if (targetLastIndex != -1) {
            return userCode.substring(0, targetLastIndex) + "\n" + mainFunc
                    + "\n" + userCode.substring(targetLastIndex);
        }
        throw new ServiceException(ResultCode.FAILED);
    }
}
