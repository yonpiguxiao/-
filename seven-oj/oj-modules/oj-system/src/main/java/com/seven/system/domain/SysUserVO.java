package com.seven.system.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysUserVO {
    @Schema(description = "用户账号")
    private String userAccount;

    @Schema(description = "用户昵称")
    private String nickName;
}
