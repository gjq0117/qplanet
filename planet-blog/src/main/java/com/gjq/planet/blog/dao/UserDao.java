package com.gjq.planet.blog.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.UserMapper;
import com.gjq.planet.common.domain.entity.User;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> {

    /**
     * 根据账户查询用户
     *
     * @param account
     * @return
     */
    public User getByAccount(String account) {
        return lambdaQuery()
                .eq(User::getUsername, account)
                .or()
                .eq(User::getPhone, account)
                .or()
                .eq(User::getEmail, account)
                .one();
    }
}
