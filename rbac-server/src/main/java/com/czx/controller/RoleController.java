//package com.czx.controller;
//
//import com.czx.utils.CommonResult;
//import com.czx.pojo.Role;
//import com.czx.service.RoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/role")
//public class RoleController {
//    @Autowired
//    private RoleService roleService;
//
//    @GetMapping("/list")
//    public CommonResult<?> getAllRoles() {
//        return CommonResult.success(roleService.getAllRoles());
//    }
//
//    @PostMapping("/add")
//    public CommonResult<?> addRole(@RequestBody Role role) {
//        System.out.println(role.getRole_name());
//        return CommonResult.success(roleService.addRole(role));
//    }
//
//    @DeleteMapping("/delete/{role_id}")
//    public CommonResult<?> deleteRole(@PathVariable long role_id) {
//        return CommonResult.success(roleService.deleteRole(role_id));
//    }
//
//    @PutMapping("/update/{role_id}")
//    public CommonResult<?> updateRole(@PathVariable long role_id, @RequestBody Role role) {
//        role.setId(role_id);
//        return CommonResult.success(roleService.updateRole(role));
//    }
//}
//

package com.czx.controller;
import com.czx.pojo.AuthToken;
import com.czx.service.RoleService;
import com.czx.pojo.Role;
import com.czx.utils.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/role")
@RestController
public class RoleController {
    @Autowired
    RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/getInfo")
    public CommonResult<?> getInfo(@RequestParam String rolename) {
        System.out.println("rolename:" + rolename);
        Role role = roleService.getInfo(rolename);

        if (role == null) {
            logger.info("角色" + rolename + "不存在");
            return CommonResult.error(400, "该角色不存在");
        }
        else {
            logger.info("角色" + rolename + "获取成功");
            System.out.println("角色获取成功");
            return CommonResult.success(role);
        }
    }

    @GetMapping("/list")
    public CommonResult<?> getAllRoles() {
        return CommonResult.success(roleService.getAllRoles());
    }

    @AuthToken
    @DeleteMapping("/delete")
    public CommonResult<?> deleteById(@RequestParam long role_id) {
        System.out.println(role_id);
        logger.info("角色删除成功");
        return CommonResult.success(roleService.deleteById(role_id));
    }

    @AuthToken
    @PostMapping("/add")
    public CommonResult<?> addRole(@RequestBody Role role) {
        String username = role.getRolename();
        String description = role.getDescription();

        System.out.println("login username:" + username);
        System.out.println("login description:" + description);

        logger.info("角色" + role.getRolename()  + "添加成功");
        return CommonResult.success(roleService.addRole(role));
    }

    @AuthToken
    @PutMapping("/update")
    public CommonResult<?> updateRole(@RequestBody Role role) {
        String rolename = role.getRolename();
        String description = role.getDescription();

        System.out.println("rolename:" + rolename);
        System.out.println("description:" + description);

        logger.info("角色" + rolename + "更新成功");
        return CommonResult.success(roleService.updateRole(role));
    }
}
