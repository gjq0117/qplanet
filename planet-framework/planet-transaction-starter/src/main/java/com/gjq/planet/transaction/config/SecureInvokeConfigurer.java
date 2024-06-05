package com.gjq.planet.transaction.config;

import org.springframework.lang.Nullable;

import java.util.concurrent.Executor;

/**
 * @author: gjq0117
 * @date: 2024/5/11 18:13
 * @description:
 */
public interface SecureInvokeConfigurer {

    /**
     *  返回一个线程池
     *
     * @return
     */
    @Nullable
    default Executor getSecureInvokeExecutor() {
        return null;
    }
}
