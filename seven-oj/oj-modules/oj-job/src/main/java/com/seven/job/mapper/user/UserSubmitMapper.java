package com.seven.job.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.job.domain.user.UserScore;
import com.seven.job.domain.user.UserSubmit;

import java.util.Set;
import java.util.List;

public interface UserSubmitMapper extends BaseMapper<UserSubmit> {

    //    where examId in(1,2,3)
    List<UserScore> selectUserScoreList(Set<Long> examIdSet);

    List<Long> selectHostQuestionList();
}