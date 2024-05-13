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

    public Boolean isExistUserName(String userName) {
        return lambdaQuery()
                .eq(User::getUsername, userName)
                .count() > 0;
    }

    public Boolean isExistEmail(String email) {
        return lambdaQuery()
                .eq(User::getEmail, email)
                .count() > 0;
    }

    public User getUserByEmail(String email) {
        return lambdaQuery()
                .eq(User::getEmail, email)
                .one();
    }

    public void modifyPwdByEmail(User user) {
        lambdaUpdate()
                .set(User::getPassword, user.getPassword())
                .eq(User::getEmail, user.getEmail())
                .update();
    }

    public User getByNickName(String nickname) {
        return lambdaQuery()
                .eq(User::getNickname, nickname)
                .one();
    }

    public Boolean changeUserStatus(Long uid, Integer status) {
        return lambdaUpdate()
                .set(User::getUserStatus, status)
                .eq(User::getId, uid)
                .update();
    }

}
