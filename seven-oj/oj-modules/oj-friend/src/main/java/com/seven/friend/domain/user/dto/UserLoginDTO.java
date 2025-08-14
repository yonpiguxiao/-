package com.seven.friend.domain.user.dto;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String phone;

    private String code;
}
