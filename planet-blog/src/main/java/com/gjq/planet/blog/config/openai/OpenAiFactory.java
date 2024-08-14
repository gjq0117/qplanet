package com.gjq.planet.blog.config.openai;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassDescription: openAi工程，注册和提供不同类型的ai模型
 * @Author: gjq
 * @Created: 2024-08-14 17:37
 */
public class OpenAiFactory {

    private static final Map<Long, OpenAiChatModel> MODEL_MAP = new HashMap<>();

    /**
     *  注册模型
     *
     * @param modelId
     * @param model
     */
    public static void register(Long modelId, OpenAiChatModel model){
        MODEL_MAP.put(modelId, model);
    }

    /**
     *  获取模型
     *
     * @param modelId
     * @return
     */
    public static OpenAiChatModel getModel(Long modelId){
        return MODEL_MAP.get(modelId);
    }

    /**
     *  获取第一个modelId（一般处理单聊情况）
     *
     * @return
     */
    public static Long getFirstModelId() {
        return CollectionUtils.firstElement(MODEL_MAP.keySet().stream().toList());
    }
}
