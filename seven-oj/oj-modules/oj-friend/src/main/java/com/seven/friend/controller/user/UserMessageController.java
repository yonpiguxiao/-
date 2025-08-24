package com.seven.friend.controller.user;


import com.seven.common.core.Controller.BaseController;
import com.seven.common.core.domain.PageQueryDTO;
import com.seven.common.core.domain.R;
import com.seven.common.core.domain.TableDataInfo;
import com.seven.friend.service.user.IUserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/message")
public class UserMessageController extends BaseController {

    @Autowired
    private IUserMessageService userMessageService;

    @GetMapping("/list")
    public TableDataInfo list(PageQueryDTO dto) {
        return userMessageService.list(dto);
    }

    @DeleteMapping("/delete")
    public R<Void> delete(Long textId)  {
        return toR(userMessageService.delete(textId));
    }
}
