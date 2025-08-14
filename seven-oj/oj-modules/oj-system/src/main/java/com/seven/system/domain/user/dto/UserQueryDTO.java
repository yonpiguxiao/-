package com.seven.system.domain.user.dto;

import com.seven.common.core.domain.PageQueryDTO;
import com.seven.system.domain.user.vo.UserVO;
import lombok.Data;

import java.util.List;

@Data
public class UserQueryDTO extends PageQueryDTO {
    private Long userId;

    private String nickName;

}
