package com.seven.common.core.domain;

import lombok.Data;

@Data
public class R<T> {
    private int code; //定义一些固定的code, 前后端商量好的

    private String msg; //对code的辅助说明

    private T data; //请求某个接口的返回的数据list 如SysUser
}
