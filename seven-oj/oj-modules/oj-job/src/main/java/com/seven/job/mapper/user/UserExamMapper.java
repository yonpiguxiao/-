package com.seven.job.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.job.domain.user.UserExam;
import com.seven.job.domain.user.UserScore;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserExamMapper extends BaseMapper<UserExam> {

    void updateUserScoreAndRank(@Param("userScoreList") List<UserScore> userScoreList);
}