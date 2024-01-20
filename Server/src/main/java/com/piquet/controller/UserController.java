package com.piquet.controller;

import com.piquet.service.UserService;
import com.piquet.pojo.Result;
import com.piquet.pojo.User;
import com.piquet.utils.JwtUtil;
import com.piquet.utils.Md5Util;
import com.piquet.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping("/register")
    public Result register(
            // 用户名：6-20位英文字符与数字的组合，且首字符不能为数字
            @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]{5,19}$") String username,
            // 密码：8-20位英文字符或数字的组合，必须有大小写，不能包含特殊字符
            @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z]).{8,20}$") String password) {

        User registerUser = userService.findByUserName(username);

        if (registerUser == null) {
            // 查无此人
            // 注册
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户已存在！");
        }
    }

    @PostMapping("/login")
    public Result login(
            // 用户名：6-20位英文字符与数字的组合，且首字符不能为数字
            @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]{5,19}$") String username,
            // 密码：8-20位英文字符或数字的组合，必须有大小写，不能包含特殊字符
            @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z]).{8,20}$") String password) {

        // 根据username查询用户
        User loginUser = userService.findByUserName(username);

        // 判断用户是否存在
        if (loginUser == null) {
            return Result.error("用户不存在");
        }

        // 判断密码是否错误
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            // 密码正确
            // 生成token
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);

            // 把token存储到redis
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);

            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @GetMapping("/nicknames")
    public Result<List<User>> nicknameList() {
        List<User> ul = userService.nickNameList();
        return Result.success(ul);
    }
}