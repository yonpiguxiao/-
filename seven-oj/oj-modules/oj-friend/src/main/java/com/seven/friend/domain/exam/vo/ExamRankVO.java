package com.seven.friend.domain.exam.vo;

import lombok.Data;

@Data
public class ExamRankVO {

    private Long userId;

    private String nickName;

    private int examRank;

    private int score;

}