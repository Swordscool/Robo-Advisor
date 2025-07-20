package com.work.login.mapper;

import com.work.login.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {
    
    /**
     * 根据用户名和密码查询用户
     * @param username 用户名
     * @param password 密码
     * @return 用户信息，如果验证失败返回null
     */
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
} 