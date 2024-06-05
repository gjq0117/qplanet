package com.gjq.planet.blog.service.strategy;

import com.gjq.planet.common.exception.CommonErrorEnum;
import com.gjq.planet.common.utils.AssertUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gjq0117
 * @date: 2024/5/30 10:34
 * @description: 消息处理器工厂
 */
public class MsgHandlerFactory {

    private static final Map<Integer, AbstractMsgHandler<?>> STRATEGY_MAP = new HashMap<>();

    /**
     * 注册处理器
     *
     * @param type     类型
     * @param strategy 处理器
     */
    public static void register(Integer type, AbstractMsgHandler<?> strategy) {
        STRATEGY_MAP.put(type, strategy);
    }

    /**
     * 通过类型获取指定处理器
     *
     * @param type 类型
     * @return 处理器
     */
    public static AbstractMsgHandler<?> getStrategyNoNull(Integer type) {
        AbstractMsgHandler<?> strategy = STRATEGY_MAP.get(type);
        AssertUtil.isNotEmpty(strategy, CommonErrorEnum.PARAM_INVALID);
        return strategy;
    }

}
