package com.seven.system.domain.sysuser;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.seven.common.core.domain.BaseEntity;
import lombok.Data;

@TableName("tb_sys_user")
@Data
public class SysUser extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId; //不再使用auto_increment

    private String nickName;

    private String userAccount;

    private String password;

}
