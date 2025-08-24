package com.seven.friend.domain.user.dto;


import lombok.Data;

@Data
public class UserSubmitDTO {

    private Long examId;

    private Long questionId;

    private Integer programType; //(0:java ; 1:cpp ; 2:golang)

    private String userCode;

}
