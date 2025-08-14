package com.seven.system.controller.user;

import com.seven.common.core.Controller.BaseController;
import com.seven.common.core.domain.R;
import com.seven.common.core.domain.TableDataInfo;
import com.seven.system.domain.user.dto.UserQueryDTO;
import com.seven.system.domain.user.dto.UserUpdateDTO;
import com.seven.system.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public TableDataInfo list(UserQueryDTO userQueryDTO) {
        return getTableDataInfo(userService.list(userQueryDTO));
    }

    @PutMapping("/updateStatus")
    //TODO 限制用户的操作
    public R<Void> updateStatus(@RequestBody UserUpdateDTO userUpdateDTO) {
        return toR(userService.updateStatus(userUpdateDTO));
    }
}
