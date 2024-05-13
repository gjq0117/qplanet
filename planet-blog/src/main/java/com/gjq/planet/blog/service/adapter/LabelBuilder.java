package com.gjq.planet.blog.service.adapter;

import com.gjq.planet.common.domain.entity.Label;
import com.gjq.planet.common.domain.vo.req.label.LabelReq;
import org.springframework.beans.BeanUtils;

/**
 * @author: gjq0117
 * @date: 2024/4/21 12:01
 * @description: 标签信息实体构建类
 */
public class LabelBuilder {

    /**
     * 通过请求信息构建标签实体
     *
     * @param req
     * @return
     */
    public static Label buildFromReq(LabelReq req) {
        Label label = new Label();
        BeanUtils.copyProperties(req, label);
        return label;
    }
}
