package com.gjq.planet.transaction.service;

import java.util.Objects;

/**
 * @author: gjq0117
 * @date: 2024/5/12 15:33
 * @description:
 */
public class SecureInvokeHolder {

    private static final ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();

    public static void setInvoking() {
        threadLocal.set(Boolean.TRUE);
    }

    public static Boolean isInvoking() {
        return Objects.nonNull(threadLocal.get());
    }

    public static void invoked() {
        threadLocal.remove();
    }
}
