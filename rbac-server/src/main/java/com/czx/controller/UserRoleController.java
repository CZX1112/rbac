package com.czx.controller;

import com.czx.pojo.AuthToken;
import com.czx.pojo.Role;
import com.czx.service.UserRoleService;
import com.czx.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userrole")
public class UserRoleController {
    @Autowired
    UserRoleService userRoleService;

    @AuthToken
    @PostMapping("/add")
    public CommonResult<?> add(@RequestParam long user_id, @RequestParam long role_id) {
        System.out.println("user_id:" + user_id);
        System.out.println("role_id:" + role_id);

        return CommonResult.success(userRoleService.add(user_id, role_id));
    }

    @AuthToken
    @PostMapping("/update")
    public CommonResult<?> update(@RequestParam long user_id, @RequestParam long role_id) {
        System.out.println("user_id:" + user_id);

        return CommonResult.success(userRoleService.update(user_id, role_id));
    }
}
