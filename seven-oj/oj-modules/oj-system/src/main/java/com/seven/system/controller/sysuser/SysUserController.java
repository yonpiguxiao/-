package com.seven.system.controller.sysuser;

import com.seven.common.core.Controller.BaseController;
import com.seven.common.core.constants.HttpConstants;
import com.seven.common.core.domain.R;
import com.seven.common.core.domain.vo.LoginUserVO;
import com.seven.system.domain.sysuser.dto.LoginDTO;
import com.seven.system.domain.sysuser.dto.SysUserSaveDTO;
import com.seven.system.domain.sysuser.vo.SysUserVO;
import com.seven.system.service.sysuser.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sysUser")
@Tag(name = "管理员接口")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService sysUserService;

    @PostMapping("/login") //安全
    @Operation(summary = "管理员登录", description = "根据账号密码进行管理员登录")
    @ApiResponse(responseCode = "1000", description = "操作成功")
    @ApiResponse(responseCode = "2000", description = "服务器繁忙请稍后重试")
    @ApiResponse(responseCode = "3102", description = "用户不存在")
    @ApiResponse(responseCode = "3103", description = "用户名或密码错误")
    public R<String> login(@RequestBody LoginDTO loginDTO) {
        return sysUserService.login(loginDTO.getUserAccount(), loginDTO.getPassword());
    }

    @DeleteMapping("/logout")
    public R<Void> logout(@RequestHeader(HttpConstants.AUTHENTICATION) String token) {
        return toR(sysUserService.logout(token));
    }


    @GetMapping("/info")
    public R<LoginUserVO> info(@RequestHeader(HttpConstants.AUTHENTICATION) String token) {
        return sysUserService.info(token);
    }


    @PostMapping("/add")
    @Operation(summary = "新增管理员", description = "根据提供的信息新增管理员")
    @ApiResponse(responseCode = "1000", description = "操作成功")
    @ApiResponse(responseCode = "2000", description = "服务器繁忙请稍后重试")
    @ApiResponse(responseCode = "3101", description = "用户已存在")
    public R<Void> add(@RequestBody SysUserSaveDTO sysUserSaveDTO) {
        return toR(sysUserService.add(sysUserSaveDTO));
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "删除⽤⼾", description = "通过⽤⼾id删除⽤⼾")
    @Parameters(value = {
            @Parameter(name = "userId", in = ParameterIn.PATH, description = "⽤⼾ID")
    })
    @ApiResponse(responseCode = "1000", description = "成功删除⽤⼾")
    @ApiResponse(responseCode = "2000", description = "服务繁忙请稍后重试")
    @ApiResponse(responseCode = "3101", description = "⽤⼾不存在")
    public R<Void> delete(@PathVariable Long userId) {
        return null;
    }


    @Operation(summary = "⽤⼾详情", description = "根据查询条件查询⽤⼾详情")
    @GetMapping("/detail")
    @Parameters(value = {
            @Parameter(name = "userId", in = ParameterIn.QUERY, description = "⽤⼾ID"),
            @Parameter(name = "sex", in = ParameterIn.QUERY, description = "⽤⼾性别")
    })
    @ApiResponse(responseCode = "1000", description = "成功获取⽤⼾信息")
    @ApiResponse(responseCode = "2000", description = "服务繁忙请稍后重试")
    @ApiResponse(responseCode = "3101", description = "⽤⼾不存在")
    public R<SysUserVO> detail(@RequestParam(required = true) Long userId, @RequestParam(required = false) String sex) {
        return null;
    }
}
