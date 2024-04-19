package com.gjq.planet.blog;

import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.oss.domain.req.UploadUrlReq;
import com.gjq.planet.oss.domain.resp.OssResp;
import com.gjq.planet.oss.service.OssService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author: gjq0117
 * @date: 2024/4/13 17:57
 * @description: 用户接口单元测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void addAdmin() {
        User user = new User();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setUsername("admin");
        user.setNickname("子笙");
        user.setPassword(bCryptPasswordEncoder.encode("admin"));
        user.setEmail("gjq0117@163.com");
        user.setGender(0);
        user.setUserStatus(1);
        user.setUserType(0);
        userDao.save(user);
    }

    @Autowired
    private OssService ossService;

    @Test
    public void testOss() {
        UploadUrlReq build = UploadUrlReq.builder().fileName("test.jpeg").scene(1).build();
        OssResp uploadUrl = ossService.getUploadUrl(build);
        System.out.println(uploadUrl);
    }
}
