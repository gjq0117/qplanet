package com.gjq.planet.transaction.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/5/11 16:12
 * @description: 请求快照参数json
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecureInvokeDTO {
    /**
     * 方法全类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法参数类型
     */
    private String parameterTypes;

    /**
     * 方法参数
     */
    private String args;
}
