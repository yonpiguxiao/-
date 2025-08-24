package com.seven.friend.service.exam.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.common.core.constants.Constants;
import com.seven.common.core.domain.TableDataInfo;
import com.seven.common.core.utils.ThreadLocalUtil;
import com.seven.friend.domain.exam.dto.ExamQueryDTO;
import com.seven.friend.domain.exam.dto.ExamRankDTO;
import com.seven.friend.domain.exam.vo.ExamRankVO;
import com.seven.friend.domain.exam.vo.ExamVO;
import com.seven.friend.domain.user.vo.UserVO;
import com.seven.friend.manager.ExamCacheManager;
import com.seven.friend.manager.UserCacheManager;
import com.seven.friend.mapper.exam.ExamMapper;
import com.seven.friend.mapper.user.UserExamMapper;
import com.seven.friend.service.exam.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements IExamService {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ExamCacheManager examCacheManager;

    @Autowired
    private UserCacheManager userCacheManager;

    @Autowired
    private UserExamMapper userExamMapper;

    @Override
    public List<ExamVO> list(ExamQueryDTO examQueryDTO) {
        PageHelper.startPage(examQueryDTO.getPageNum(), examQueryDTO.getPageSize());
        return examMapper.selectExamList(examQueryDTO);
    }

    @Override
    public TableDataInfo redisList(ExamQueryDTO examQueryDTO) {

        Long total = examCacheManager.getListSize(examQueryDTO.getType(), null);
        List<ExamVO> examVOList;

        if(total == null || total <= 0) {
            examVOList = list(examQueryDTO);
            examCacheManager.refreshCache(examQueryDTO.getType(), null);
            total = new PageInfo<>(examVOList).getTotal();
        } else {

            examVOList = examCacheManager.getExamVOList(examQueryDTO, null);
            total = examCacheManager.getListSize(examQueryDTO.getType(), null);
            if(examQueryDTO.getStartTime() != null && examQueryDTO.getEndTime() != null)
            {
                examVOList = list(examQueryDTO);
                total = new PageInfo<>(examVOList).getTotal();
            }
        }
        if(CollectionUtil.isEmpty(examVOList)) {
            return TableDataInfo.empty();
        }
        assembleExamVOList(examVOList);
        return TableDataInfo.success(examVOList, total);
    }

    @Override
    public TableDataInfo rankList(ExamRankDTO examRankDTO) {
        Long total = examCacheManager.getRankListSize(examRankDTO.getExamId());
        List<ExamRankVO> examRankVOList;

        if(total == null || total <= 0) {
            PageHelper.startPage(examRankDTO.getPageNum(), examRankDTO.getPageSize());
            examRankVOList = userExamMapper.selectExamRankList(examRankDTO.getExamId());
            examCacheManager.refreshExamRankCache(examRankDTO.getExamId());
            total = new PageInfo<>(examRankVOList).getTotal();
        } else {
            examRankVOList = examCacheManager.getExamRankList(examRankDTO);
            total = examCacheManager.getRankListSize(examRankDTO.getExamId());
        }
        if(CollectionUtil.isEmpty(examRankVOList)) {
            return TableDataInfo.empty();
        }
        assembleExamRankVOList(examRankVOList);
        return TableDataInfo.success(examRankVOList, total);
    }

    private void assembleExamRankVOList(List<ExamRankVO> examRankVOList) {
        if(CollectionUtil.isEmpty(examRankVOList)) {
            return;
        }
        for (ExamRankVO examRankVO : examRankVOList) {
            Long userId = examRankVO.getUserId();
            UserVO user = userCacheManager.getUserById(userId);
            examRankVO.setNickName(user.getNickName());
        }
    }


    @Override
    public String getFirstQuestion(Long examId) {
        checkAndRefresh(examId);
        return examCacheManager.getFirstQuestion(examId).toString();
    }

    @Override
    public String preQuestion(Long examId, Long questionId) {
        checkAndRefresh(examId);
        return examCacheManager.preQuestion(examId, questionId).toString();
    }

    @Override
    public String nextQuestion(Long examId, Long questionId) {
        checkAndRefresh(examId);
        return examCacheManager.nextQuestion(examId, questionId).toString();
    }


    private void checkAndRefresh(Long examId) {
        Long listSize = examCacheManager.getExamQuestionListSize(examId);
        if(listSize == null || listSize <= 0) {
            examCacheManager.refreshExamQuestionCache(examId);
        }
    }

    private void assembleExamVOList(List<ExamVO> examVOList) {
        Long userId = ThreadLocalUtil.get(Constants.USER_ID, Long.class);
        List<Long> userExamIdList = examCacheManager.getAllUserExamList(userId);
        if(CollectionUtil.isEmpty(userExamIdList)) {
            return;
        }
        for(ExamVO examVO : examVOList) {
            if(userExamIdList.contains(examVO.getExamId())) {
                examVO.setEnter(true);
            }
        }
    }
}
