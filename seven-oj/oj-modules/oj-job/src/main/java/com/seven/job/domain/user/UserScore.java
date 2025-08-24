package com.seven.job.domain.user;

import lombok.Data;

@Data
public class UserScore {

    private Long examId;

    private Long userId;

    private int score;

    private int examRank;
}