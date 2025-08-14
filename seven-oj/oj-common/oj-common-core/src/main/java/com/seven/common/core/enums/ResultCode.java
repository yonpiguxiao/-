package com.seven.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {
    //操作成功
    SUCCESS (1000, "操作成功"),

    //服务器内部错误，友好提⽰
    ERROR (2000, "服务繁忙请稍后重试"),

    //操作失败，但是服务器不存在异常
    FAILED (3000, "操作失败"),
    FAILED_UNAUTHORIZED (3001, "未授权"),
    FAILED_PARAMS_VALIDATE (3002, "参数校验失败"),
    FAILED_NOT_EXISTS (3003, "资源不存在"),
    FAILED_ALREADY_EXISTS (3004, "资源已存在"),


    AILED_USER_EXISTS (3101, "⽤⼾已存在"),
    FAILED_USER_NOT_EXISTS (3102, "⽤⼾不存在"),
    FAILED_LOGIN (3103, "账号或密码错误"),
    FAILED_USER_BANNED (3104, "您已被列⼊⿊名单, 请联系管理员."),
    FAILED_USER_PHONE(3105, "您输入的手机号有误"),
    FAILED_FREQUENT(3106, "操作频繁,请稍后重试."),
    FAILED_TIME_LIMIT(3107, "当天请求次数已到达上线"),
    FAILED_SEND_CODE(3108, "验证码发送错误"),
    FAILED_INVALID_CODE(3109, "验证码无效"),
    FAILED_ERROR_CODE(3110, "验证码错误"),


    EXAM_START_TIME_BEFORE_CURRENT_TIME(3201, "竞赛开始时间不能早于当前时间"),
    EXAM_END_TIME_BEFORE_START_TIME(3202, "竞赛结束时间不能早于开始时间"),
    EXAM_NOT_EXISTS (3203, "竞赛不存在"),
    EXAM_QUESTION_NOT_EXISTS (3204, "为竞赛新增的题目不存在"),
    EXAM_STARTED(3205, "竞赛已经开始, 无法进行操作"),
    EXAM_NOT_HAS_QUESTION(3206, "竞赛当中不包含题目"),
    EXAM_IS_FINISH(3207, "竞赛已经结束不能进行操作");

    private int code;

    private String msg;
}
