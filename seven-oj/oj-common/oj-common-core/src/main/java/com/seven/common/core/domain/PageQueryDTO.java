package com.seven.common.core.domain;

import lombok.Data;

@Data
public class PageQueryDTO {

    private Integer pageSize = 10;

    private Integer pageNum = 1;
}
