package com.seven.friend.service.user;

import com.seven.common.core.domain.PageQueryDTO;
import com.seven.common.core.domain.TableDataInfo;

public interface IUserMessageService {
    TableDataInfo list(PageQueryDTO dto);

    int delete(Long textId);
}
