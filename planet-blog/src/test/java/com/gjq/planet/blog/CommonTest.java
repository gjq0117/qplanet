package com.gjq.planet.blog;

import com.gjq.planet.blog.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    public void testRedisExpire() {
        RedisUtils.set("redisExpire","aaaa",10,TimeUnit.SECONDS);
    }
}
