package com.gjq.planet.transaction.aspect;

import cn.hutool.core.date.DateUtil;
import com.gjq.planet.transaction.annotation.SecureInvoke;
import com.gjq.planet.transaction.domain.dto.SecureInvokeDTO;
import com.gjq.planet.transaction.domain.entity.SecureInvokeRecord;
import com.gjq.planet.transaction.service.SecureInvokeHolder;
import com.gjq.planet.transaction.service.SecureInvokeService;
import com.gjq.planet.transaction.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author: gjq0117
 * @date: 2024/5/11 16:00
 * @description:
 */
@Slf4j
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 1) // 确保最先执行
public class SecureInvokeAspect {

    @Autowired
    private SecureInvokeService secureInvokeService;

    /**
     *  环绕通知
     *
     * @param joinPoint 接入点
     * @param secureInvoke 切面
     */
    @Around("@annotation(secureInvoke)")
    public Object around(ProceedingJoinPoint joinPoint, SecureInvoke secureInvoke) throws Throwable {
        boolean async = secureInvoke.async();
        // 是否存在事务
        boolean actualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        if (SecureInvokeHolder.isInvoking() || !actualTransactionActive) {
            // 如果此方法没有标注事务则不做任何处理
            joinPoint.proceed();
        }
        // 构建调用的方法信息
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        List<String> parameters = Stream.of(method.getParameterTypes()).map(Class::getName).collect(Collectors.toList());
        SecureInvokeDTO secureInvokeDTO = SecureInvokeDTO.builder()
                .args(JsonUtils.toStr(joinPoint.getArgs()))
                .className(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(JsonUtils.toStr(parameters))
                .build();
        SecureInvokeRecord record = SecureInvokeRecord.builder()
                .secureInvokeDTO(secureInvokeDTO)
                .maxRetryTimes(secureInvoke.maxRetryTimes())
                .nextRetryTime(DateUtil.offsetMinute(new Date(), (int) SecureInvokeService.RETRY_INTERVAL_MINUTES))
                .build();
        secureInvokeService.invoke(record, async);
        return null;
    }
}
