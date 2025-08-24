package com.seven.friend.controller.user;


import com.seven.common.core.Controller.BaseController;
import com.seven.common.core.constants.HttpConstants;
import com.seven.common.core.domain.R;
import com.seven.common.core.domain.vo.LoginUserVO;
import com.seven.friend.domain.user.dto.UserCodeDTO;
import com.seven.friend.domain.user.dto.UserLoginDTO;
import com.seven.friend.domain.user.dto.UserUpdateDTO;
import com.seven.friend.domain.user.vo.UserVO;
import com.seven.friend.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    IUserService userService;

    @PostMapping("/sendCode")
    public R<Void> sendCode(@RequestBody UserCodeDTO userCodeDTO) {
        return toR(userService.sendCode(userCodeDTO));
    }

    @PostMapping("/code/login")
    public R<String> codeLogin(@RequestBody UserLoginDTO userLoginDTO) {
        return R.ok(userService.codeLogin(userLoginDTO.getPhone(), userLoginDTO.getCode()));
    }

    @DeleteMapping("/logout")
    public R<Void> logout(@RequestHeader(HttpConstants.AUTHENTICATION) String token) {
        return toR(userService.logout(token));
    }

    @GetMapping("/info")
    public R<LoginUserVO> info(@RequestHeader(HttpConstants.AUTHENTICATION) String token) {
        return userService.info(token);
    }

    @GetMapping("/detail")
    public R<UserVO> detail() {
        return R.ok(userService.detail());
    }

    @PutMapping("/edit")
    public R<Void> edit(@RequestBody UserUpdateDTO userUpdateDTO) {
        return toR(userService.edit(userUpdateDTO));
    }

    @PutMapping("/head-image/update")
    public R<Void> updateHeadImage(@RequestBody UserUpdateDTO userUpdateDTO) {
        return toR(userService.updateHeadImage(userUpdateDTO));
    }
}
