package com.seven.friend.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.seven.common.core.domain.BaseEntity;
import lombok.Data;

@Data
@TableName("tb_user_exam")
public class UserExam extends BaseEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "USER_EXAM_ID", type = IdType.ASSIGN_ID)
    private Long userExamId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long examId;

    private Integer score;

    private Integer examRank;
}
