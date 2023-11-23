package com.czx.controller;

import com.czx.pojo.AuthToken;
import com.czx.pojo.Permission;
import com.czx.service.PermissionService;
import com.czx.utils.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/list")
    public CommonResult<?> getAllPermissions() {
        return CommonResult.success(permissionService.getAllPermissions());
    }

    @AuthToken
    @PostMapping("/add")
    public CommonResult<?> add(@RequestParam long role_id, @RequestParam long permission_id) {
        System.out.println("role_id:" + role_id);
        System.out.println("permission_id:" + permission_id);

        return CommonResult.success(permissionService.add(role_id, permission_id));
    }

    @AuthToken
    @DeleteMapping("/delete")
    public CommonResult<?> deleteById(@RequestParam long role_id) {
        System.out.println(role_id);
        return CommonResult.success(permissionService.deleteById(role_id));
    }

    @GetMapping("/getByPermissionname")
    public CommonResult<?> getByPermissionname(@RequestParam String permission_name) {
        System.out.println("permission_name:" + permission_name);
        Permission permission = permissionService.getByPermissionname(permission_name);

        if (permission == null) {
            logger.info("权限" + permission_name + "不存在");
            return CommonResult.error(400, "该权限不存在");
        }
        else {
            logger.info("权限" + permission_name + "获取成功");
            System.out.println("权限获取成功");
            return CommonResult.success(permission);
        }
    }

    @GetMapping("/getByUsername")
    public CommonResult<?> getByUsername(@RequestParam String username) {
        System.out.println("username:" + username);
        logger.info("根据用户" + username + "获取权限成功");
        return CommonResult.success(permissionService.getByUsername(username));
    }

    @PostMapping("/addPermission")
    public CommonResult<?> addPermission(@RequestBody Permission permission) {
        logger.info("权限" + permission.getPermission_name()  + "添加成功");
        return CommonResult.success(permissionService.addPermission(permission));
    }

    @DeleteMapping("/deleteByPermissionId")
    public CommonResult<?> deleteByPermissionId(@RequestParam long permission_id) {
        System.out.println(permission_id);
        logger.info("权限删除成功");
        return CommonResult.success(permissionService.deleteByPermissionId(permission_id));
    }

    @PutMapping("/update")
    public CommonResult<?> updatePermission(@RequestBody Permission permission) {
        String permission_name = permission.getPermission_name();
        String description = permission.getDescription();
        String url = permission.getUrl();

        System.out.println("permission_name:" + permission_name);
        System.out.println("description:" + description);
        System.out.println("url:" + url);

        logger.info("权限" + permission_name + "更新成功");
        return CommonResult.success(permissionService.updatePermission(permission));
    }
}
