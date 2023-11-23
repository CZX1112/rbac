package com.czx.controller;

import com.czx.pojo.*;
import com.czx.service.PermissionService;
import com.czx.service.UserService;
import com.czx.utils.CommonResult;
import com.czx.utils.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    PermissionService permissionService;
    @Value("${jwt.access_token.expiration}")
    private Long accessTokenExpiration;
    @Value("${jwt.refresh_token.expiration}")
    private Long refreshTokenExpiration;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/refresh-token")
    public CommonResult<TokenResponse> refreshToken(@RequestBody TokenResponse tokenResponse){

        String refreshToken = tokenResponse.getRefreshToken();

        // 先判断refreshToken是否在Redis中
        String username = redisTemplate.opsForValue().get(refreshToken);

        System.out.println("username = " + username);
        System.out.println("refreshToken = " + refreshToken);

        if (username == null) { // refreshToken不存在
            logger.info("refreshToken失效，accessToken更新失败，需要重新登陆验证！");
            return CommonResult.error(0, "Refresh token expired");
        }
        System.out.println("测试一下是否还能过来");
        String accessToken = jwtTokenUtil.generateAccessToken(username);
        redisTemplate.opsForValue().set(accessToken, username);
        tokenResponse.setAccessToken(accessToken);
        logger.info("accessToken更新完成！");
        return CommonResult.success(tokenResponse);
    }

    @PostMapping("/logout")
    public CommonResult<?> logout(@RequestBody TokenResponse token){

        redisTemplate.delete(token.getAccessToken());
        redisTemplate.delete(token.getRefreshToken());

        logger.info("用户登出成功！");
        return CommonResult.success("退出成功");
    }

    @PostMapping("/login")
    public CommonResult<?> login(@RequestBody User data) {
        // 设置键值对
        redisTemplate.opsForValue().set("abcd_token", "user01");
        // 获取键对应的值
        logger.info("This is an info log message.");
        String value = (String) redisTemplate.opsForValue().get("abcd_token");
        System.out.println("value = " + value);
        // 进行登录验证逻辑
        System.out.println(1);
        User user = userService.getUserByUsername(data.getUsername());

        if (user != null && user.getPassword().equals(data.getPassword())) {
            ;
        } else
        {
            logger.info("登陆失败，用户名或密码错误！");
            return CommonResult.error(400,"登录失败"); // 登录失败
        }

        System.out.println(2);
        String username = data.getUsername();
        String accessToken = jwtTokenUtil.generateAccessToken(username);
        String refreshToken = jwtTokenUtil.generateRefreshToken(username);

        redisTemplate.opsForValue().set(accessToken, username, accessTokenExpiration, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(refreshToken, username, refreshTokenExpiration, TimeUnit.SECONDS);

        List<Permission> permissions = permissionService.getByUsername(username);

        System.out.println("begin login");
        System.out.println(permissions);
        System.out.println("end login");

        // 将permissions列表序列化为JSON字符串
        String permissionsJson = convertPermissionsListToJson(permissions);

        // 使用唯一的键将JSON字符串存储到Redis中
        String permissionsKey = "permissions:" + username;
        redisTemplate.opsForValue().set(permissionsKey, permissionsJson);

        // 从 Redis 中获取权限列表的字符串
        String permissionsString = redisTemplate.opsForValue().get(permissionsKey);

        // 将逗号分隔的字符串转换为权限列表
        List<String> permissionsList = Arrays.asList(permissionsString.split(","));

        System.out.println("123456789");
        System.out.println(permissionsList);
        System.out.println(permissionsList.get(0));
        System.out.println("123456789");

        TokenResponse token_resp = new TokenResponse(accessToken,refreshToken);

        CommonResult<TokenResponse> result = CommonResult.success(token_resp);
        logger.info("用户" + username + "登陆成功！");

        return result;
    }

    // 辅助方法，将Permissions列表转换为JSON字符串
    private String convertPermissionsListToJson(List<Permission> permissions) {
        // 使用你选择的JSON库将permissions列表转换为JSON字符串
        // 这里假设你使用的是Jackson库
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(permissions);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // 处理序列化异常
            return null;
        }
    }

    @GetMapping("/")
    public CommonResult<?> getUserProfile(@RequestHeader("Authorization") String authHeader) {
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        try {
            String username = jwtTokenUtil.getUsernameFromToken(token);

            User foundUser = userService.getUserByUsername(username);

            CommonResult<User> result = CommonResult.success(foundUser);
            return result;
        }
        catch (Exception e) {
            logger.info("JWT解析失败！需要重新更新accessToken！");
            return CommonResult.error(401, "JWT解析失败");
        }
    }

    @GetMapping("/menu")
    public CommonResult<?> getMenu(@RequestHeader("Authorization") String authHeader) {
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        try {
            String username = jwtTokenUtil.getUsernameFromToken(token);

            User foundUser = userService.getUserByUsername(username);
            System.out.println(foundUser);
            List<Menu> menuList;
            menuList = userService.getMenuByUsername(username);
            System.out.println(menuList);
            CommonResult<List<Menu>> result = CommonResult.success(menuList);
            //CommonResult<User> result = CommonResult.success(foundUser);
            return result;
        }
        catch (Exception e) {
            logger.info("JWT解析失败！需要重新更新accessToken！");
            return CommonResult.error(401, "JWT解析失败");
        }
    }

    @GetMapping("/list")
    public CommonResult<?> getAllUsers() {
//        try {
//            CommonResult result = CommonResult.success(userService.getAllUsers());
//            return result;
//        }
//        catch (Exception e) {
//            return CommonResult.error(401, "获取用户失败");
//        }
        return CommonResult.success(userService.getAllUsers());
    }

    @AuthToken
    @PostMapping("/add")
    public CommonResult<?> addUser(@RequestBody User user) {
        logger.info("用户" + user.getUsername()  + "添加成功");
        return CommonResult.success(userService.addUser(user));
    }

    @AuthToken
    @DeleteMapping("/delete")
    public CommonResult<?> deleteUser(@RequestHeader("Authorization") String authHeader,@RequestParam long user_id) {
        String token = authHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        //根据用户名获取用户id
        long this_id=userService.getUserByUsername(username).getUser_id();

        //根据用户id获取权限
        logger.info("用户删除成功");
        return CommonResult.success(userService.deleteUser(user_id));
    }

    @AuthToken
    @PutMapping("/update")
    public CommonResult<?> updateUser(@RequestParam long user_id, @RequestBody User user) {
        user.setId(user_id);
        logger.info("用户更新成功");
        return CommonResult.success(userService.updateUser(user));
    }

    @GetMapping("/getRoleByUsername")
    public CommonResult<?> getRoleByUsername(@RequestParam String username) {
        System.out.println("username:" + username);
        Role role = userService.getRoleByUsername(username);

        if (role == null) {
            logger.info("暂未为用户" + username  + "设置角色");
            return CommonResult.error(400, "暂未为该用户设置角色");
        }
        else {
            System.out.println("用户角色获取成功");
            return CommonResult.success(role);
        }
    }

    @GetMapping("/getRoleByUserid")
    public CommonResult<?> getRoleByUserid(@RequestParam long user_id) {
        System.out.println("user_id:" + user_id);
        Role role = userService.getRoleByUserid(user_id);

        if (role == null) {
            logger.info("暂未为该用户设置角色");
            return CommonResult.error(400, "暂未为该用户设置角色");
        }
        else {
            logger.info("用户角色获取成功");
            System.out.println("用户角色获取成功");
            return CommonResult.success(role);
        }
    }

    @GetMapping("/getUser")
    public CommonResult<?> getUser(@RequestParam String username) {
        System.out.println("username:" + username);
        User user = userService.getUser(username);

        if (user == null) {
            logger.info("用户" + username + "不存在");
            return CommonResult.error(400, "该用户不存在");
        }
        else {
            logger.info("用户" + username + "获取成功");
            System.out.println("用户获取成功");
            return CommonResult.success(user);
        }
    }
}
