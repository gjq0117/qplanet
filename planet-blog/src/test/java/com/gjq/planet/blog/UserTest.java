package com.gjq.planet.blog;

import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.dao.WebInfoDao;
import com.gjq.planet.blog.service.StatisticsService;
import com.gjq.planet.blog.utils.JsonUtils;
import com.gjq.planet.blog.utils.RedisUtils;
import com.gjq.planet.common.constant.RedisKey;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.entity.WebInfo;
import com.gjq.planet.common.domain.vo.resp.article.ArticleResp;
import com.gjq.planet.oss.domain.req.UploadUrlReq;
import com.gjq.planet.oss.domain.resp.OssResp;
import com.gjq.planet.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author: gjq0117
 * @date: 2024/4/13 17:57
 * @description: 用户接口单元测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
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

    @Test
    public void testJson() {
        String jsonStr = RedisUtils.getStr(RedisKey.getKey(RedisKey.ARTICLE_LIST));
        if (!StringUtils.isBlank(jsonStr)) {
            // redis中存在
            List<ArticleResp> articleListRespList = JsonUtils.toList(jsonStr, ArticleResp.class);
            System.out.println(articleListRespList);
        }
    }

    @Autowired
    private WebInfoDao webInfoDao;

    @Test
    public void testJson1() throws InterruptedException {
        List<WebInfo> list = new ArrayList<>();
        WebInfo webInfo = webInfoDao.getFirstOne();
        list.add(webInfo);
        list.add(webInfo);

        String jsonStr = JsonUtils.toStr(list);
        RedisUtils.set("test:", jsonStr);
        Thread.sleep(2000);
        String test = RedisUtils.getStr("test:");
        List<WebInfo> webInfoList = JsonUtils.toList(test, WebInfo.class);
        System.out.println(webInfoList);
    }

    @Test
    public void testRedisHash() {
        List<User> userList = new ArrayList();
        User user = userDao.getById(3);
        User user1 = userDao.getById(6);
        userList.add(user);
        userList.add(user1);

//        // 设置
        for (User item :
                userList) {
            RedisUtils.hset("userListTest:",item.getId().toString(),JsonUtils.toStr(item));
        }

        Map<Object, Object> map = RedisUtils.hmget("userListTest:");
        List<User> collect = map.values().stream().map(o -> {
                    User user2 = JsonUtils.toObj((String) o, User.class);
                    return user2;
                }
        ).collect(Collectors.toList());

        System.out.println(collect.get(0));
    }

    @Autowired
    private StatisticsService statisticsService;

    @Test
    public void testStatistics() {
//        System.out.println(statisticsService.sevenDayVisitCount());
        log.error("测试日志文件");
    }
}
