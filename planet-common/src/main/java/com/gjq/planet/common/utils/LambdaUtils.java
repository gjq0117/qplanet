package com.gjq.planet.common.utils;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.SneakyThrows;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.lang.reflect.Field;

/**
 * @author: gjq0117
 * @date: 2024/5/24 16:01
 * @description:
 */
public class LambdaUtils {

    @SneakyThrows
    public static <T> Class<?> getReturnType(SFunction<T, ?> func) {
        com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda lambda = com.baomidou.mybatisplus.core.toolkit.LambdaUtils.resolve(func);
        Class<?> aClass = lambda.getInstantiatedType();
        String fieldName = PropertyNamer.methodToProperty(lambda.getImplMethodName());
        Field field = aClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.getType();
    }
}
