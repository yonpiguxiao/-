package com.seven.friend.domain.user.dto;


import lombok.Data;

@Data
public class UserUpdateDTO {
    private String nickName;

    private Integer sex;

    private String phone;

    private String email;

    private String wechat;

    private String schoolName;

    private String majorName;

    private String introduce;

    private String headImage;
}
