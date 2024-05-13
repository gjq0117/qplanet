package com.gjq.planet.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: gjq0117
 * @date: 2024/4/22 17:58
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ThreadPoolTest {

    @Autowired
    private ThreadPoolExecutor executor;

    @Test
    public void test() throws ExecutionException, InterruptedException {
        long startTime = new Date().getTime();

//        CompletableFuture<String> stringCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(2000);
//                return "我执行玩了1" + Thread.currentThread().getName();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }, executor);

        CompletableFuture<String> stringCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                int i = 1 / 0;
                 throw new RuntimeException(Thread.currentThread().getName()+": 我抛出异常了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }, executor);
//        System.out.println(stringCompletableFuture1.get());
        System.out.println(stringCompletableFuture2.get());
        long endTime = new Date().getTime();
        System.out.println(endTime - startTime);
    }
}
