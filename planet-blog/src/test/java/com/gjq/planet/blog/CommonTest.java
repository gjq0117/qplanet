package com.gjq.planet.blog;

import cn.hutool.core.util.RandomUtil;
import com.gjq.planet.blog.cache.redis.batch.RoomCache;
import com.gjq.planet.blog.cache.redis.batch.UserSummerInfoCache;
import com.gjq.planet.blog.dao.MessageDao;
import com.gjq.planet.blog.dao.RoomDao;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.common.domain.entity.Room;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.enums.blog.SystemRoleEnum;
import com.gjq.planet.common.enums.common.YesOrNoEnum;
import com.gjq.planet.common.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author: gjq0117
 * @date: 2024/5/15 08:58
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CommonTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testRedisExpire() {
        RedisUtils.set("redisExpire", "aaaa", 10, TimeUnit.SECONDS);
    }

    @Test
    public void addUser() {
        // 构建测试用户
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        for (int i = 0; i < 50; i++) {
            User test = User.builder()
                    .userType(SystemRoleEnum.CUSTOMER.getCode())
                    .username("test" + i)
                    .nickname("test" + i)
                    .password(bCryptPasswordEncoder.encode("test"))
                    .gender(RandomUtil.randomInt(2))
                    .avatar("https://minio.qplanet.cn/planet/user/3ea6beec64369c2642b92c6726f1epng.png")
                    .userStatus(YesOrNoEnum.YES.getCode())
                    .build();
            userDao.save(test);
        }
    }

    @Autowired
    private UserSummerInfoCache userSummerInfoCache;

    @Test
    public void testBatchCache() {
        // setList
//        List<UserSummerInfoDTO> list = new ArrayList<>();
//        list.add(UserSummerInfoDTO.builder().uid(1L).nickname("小明").build());
//        list.add(UserSummerInfoDTO.builder().uid(2L).nickname("小红").build());
//        userSummerInfoCache.setBatch(list);

        // get
//        System.out.println(userSummerInfoCache.get(1));

        // put
//        userSummerInfoCache.put(UserSummerInfoDTO.builder().uid(3L).nickname("小高").build());

        // getBatch
//        List<UserSummerInfoDTO> batch = userSummerInfoCache.getBatch(Arrays.asList(1, 2));
//        for (UserSummerInfoDTO userSummerInfoDTO : batch) {
//            System.out.println(userSummerInfoDTO);
//        }

        // remove
//        userSummerInfoCache.remove(1);

        // removeBatch
//        userSummerInfoCache.removeBatch(Arrays.asList(2,3));
    }

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomCache roomCache;

    @Test
    public void test() {
        Room room = roomDao.getById(1);
        Room before = roomCache.get(room.getId());
        System.out.println(before);

        roomCache.put(room);
        Room after = roomCache.get(room.getId());
        System.out.println(after);
    }
}
