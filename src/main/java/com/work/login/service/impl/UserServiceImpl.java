package com.work.login.service.impl;

import com.work.login.entity.User;
import com.work.login.mapper.UserMapper;
import com.work.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        // 调用Mapper进行数据库验证
        return userMapper.findByUsernameAndPassword(username, password);
    }
} 