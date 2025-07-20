package com.work.login.service;

import com.work.login.entity.User;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 用户登录验证
     * @param username 用户名
     * @param password 密码
     * @return 用户信息，验证失败返回null
     */
    User login(String username, String password);
} 