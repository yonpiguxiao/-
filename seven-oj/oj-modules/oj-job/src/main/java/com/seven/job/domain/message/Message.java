package com.seven.job.domain.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.seven.common.core.domain.BaseEntity;
import lombok.Data;

@Data
@TableName("tb_message")
public class Message extends BaseEntity {

    @TableId(value = "MESSAGE_ID", type = IdType.ASSIGN_ID)
    private Long messageId;

    private Long textId;

    private Long sendId;

    private Long recId;
}