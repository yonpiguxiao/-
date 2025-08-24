package com.seven.friend.service.user.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.common.core.constants.Constants;
import com.seven.common.core.domain.TableDataInfo;
import com.seven.common.core.enums.ExamListType;
import com.seven.common.core.enums.ResultCode;
import com.seven.common.core.utils.ThreadLocalUtil;
import com.seven.common.security.exception.ServiceException;
import com.seven.common.security.service.TokenService;
import com.seven.friend.domain.exam.Exam;
import com.seven.friend.domain.exam.dto.ExamQueryDTO;
import com.seven.friend.domain.exam.vo.ExamVO;
import com.seven.friend.domain.user.UserExam;
import com.seven.friend.domain.user.vo.UserVO;
import com.seven.friend.manager.ExamCacheManager;
import com.seven.friend.manager.UserCacheManager;
import com.seven.friend.mapper.exam.ExamMapper;
import com.seven.friend.mapper.user.UserExamMapper;
import com.seven.friend.service.user.IUserExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserExamServiceImp implements IUserExamService {
    @Autowired
    private UserExamMapper userExamMapper;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ExamCacheManager examCacheManager;

    @Value("${jwt.secret}")
    private String secret;

    private UserCacheManager userCacheManager;

    @Override
    public int enter(String token, Long examId) {
//        Long userId = ThreadLocalUtil.get(Constants.USER_ID, Long.class);
//        UserVO user = userCacheManager.getUserById(userId);
//        if(user.getStatus() == 0) {
//            throw new ServiceException(ResultCode.FAILED_USER_BANNED);
//        }
        Exam exam = examMapper.selectById(examId);
        if(exam == null) {
            throw new ServiceException(ResultCode.EXAM_NOT_EXISTS);
        }
        if(exam.getStartTime().isBefore(LocalDateTime.now())) {
            throw new ServiceException(ResultCode.EXAM_STARTED);
        }
//        Long userId = tokenService.getUserId(token, secret);
        Long userId = ThreadLocalUtil.get(Constants.USER_ID, Long.class);
        UserExam userExam = userExamMapper.selectOne(new LambdaQueryWrapper<UserExam>()
                .eq(UserExam::getExamId, examId)
                .eq(UserExam::getUserId, userId));
        if(userExam != null) {
            throw new ServiceException(ResultCode.USER_EXAM_HAS_ENTER);
        }
        examCacheManager.addUserExamCache(userId, examId);
        userExam = new UserExam();
        userExam.setExamId(examId);
        userExam.setUserId(userId);
        return userExamMapper.insert(userExam);
    }

    @Override
    public TableDataInfo list(ExamQueryDTO examQueryDTO) {
        Long userId = ThreadLocalUtil.get(Constants.USER_ID, Long.class);
        Long total = examCacheManager.getListSize(ExamListType.USER_EXAM_LIST.getValue(), userId);
        List<ExamVO> examVOList;
        examQueryDTO.setType(ExamListType.USER_EXAM_LIST.getValue());
        if(total == null || total <= 0) {
            PageHelper.startPage(examQueryDTO.getPageNum(), examQueryDTO.getPageSize());
            examVOList = userExamMapper.selectUserExamList(userId);
            examCacheManager.refreshCache(ExamListType.USER_EXAM_LIST.getValue(), userId);
            total = new PageInfo<>(examVOList).getTotal();
        } else {
            examVOList = examCacheManager.getExamVOList(examQueryDTO, userId);
            total = examCacheManager.getListSize(examQueryDTO.getType(), userId);
        }
        if(CollectionUtil.isEmpty(examVOList)) {
            return TableDataInfo.empty();
        }
        return TableDataInfo.success(examVOList, total);
    }
}
