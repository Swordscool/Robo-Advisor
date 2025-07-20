package com.work.login.controller;

import com.work.login.entity.User;
import com.work.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*") // 允许跨域请求
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口
     * @param loginData 登录数据
     * @return 登录结果
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        Map<String, Object> result = new HashMap<>();
        
        String username = loginData.get("username");
        String password = loginData.get("password");
        
        // 参数验证
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "用户名和密码不能为空");
            result.put("data", null);
            return result;
        }
        
        // 调用服务进行登录验证
        User user = userService.login(username.trim(), password.trim());
        
        if (user != null) {
            // 登录成功
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("data", user);
        } else {
            // 登录失败
            result.put("success", false);
            result.put("message", "用户名或密码错误");
            result.put("data", null);
        }
        
        return result;
    }
} 