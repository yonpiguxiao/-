package com.seven.common.core.domain;

import com.seven.common.core.enums.ResultCode;
import lombok.Data;

@Data
public class R<T> {
    private int code; //定义一些固定的code, 前后端商量好的

    private String msg; //对code的辅助说明

    private T data; //请求某个接口的返回的数据list 如SysUser

    public static <T> R<T> ok() {
        return assembleResult(null, ResultCode.SUCCESS);
    }

    public static <T> R<T> ok(T data) {
        return assembleResult(data, ResultCode.SUCCESS);
    }

    public static <T> R<T> fail() {
        return assembleResult(null, ResultCode.FAILED);
    }

    public static <T> R<T> fail(ResultCode resultCode) {
        return assembleResult(null, resultCode);
    }

    private static <T> R<T> assembleResult(T data, ResultCode resultCode) {
        R<T> r = new R<>();
        r.setCode(resultCode.getCode());
        r.setMsg(resultCode.getMsg());
        r.setData(data);
        return r;
    }
}
