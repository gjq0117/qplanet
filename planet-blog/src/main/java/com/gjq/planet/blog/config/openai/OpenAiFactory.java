package com.gjq.planet.blog.config.openai;

import com.gjq.planet.common.domain.entity.Robot;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassDescription: openAi工程，注册和提供不同类型的ai模型
 * @Author: gjq
 * @Created: 2024-08-14 17:37
 */
public class OpenAiFactory {

    private static final Map<Long, OpenAiChatModel> MODEL_MAP = new HashMap<>();

    /**
     *  创建模型
     *
     * @param robot
     * @return
     */
    private static OpenAiChatModel createModel(Robot robot) {
        OpenAiApi openAiApi = new OpenAiApi(robot.getBaseUrl(), robot.getApiKey());
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel(robot.getModel())
                .withTemperature(robot.getTemperature())
                .build();
        return new OpenAiChatModel(openAiApi, options);
    }

    /**
     *  注册模型
     *
     * @param modelId
     * @param model
     */
    private static void setModel(Long modelId, OpenAiChatModel model){
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
     *  获取第一个model（一般处理单聊情况）
     *
     * @return
     */
    public static Long getFirstModelId() {
        return CollectionUtils.firstElement(MODEL_MAP.keySet().stream().toList());
    }

    /**
     *  移除模型
     *
     * @param modelId
     */
    public static void removeModel(Long modelId) {
        MODEL_MAP.remove(modelId);
    }

    /**
     *  启用机器人
     *
     * @param robot 机器人
     */
    public static void register(Robot robot) {
        if (Objects.isNull(MODEL_MAP.get(robot.getId()))) {
            OpenAiChatModel model = createModel(robot);
            setModel(robot.getId(),model);
        }
    }
}
