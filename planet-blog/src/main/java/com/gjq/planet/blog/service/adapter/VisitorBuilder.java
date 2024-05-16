package com.gjq.planet.blog.service.adapter;

import com.gjq.planet.common.domain.entity.IpDetail;
import com.gjq.planet.common.domain.entity.Visitor;
import com.gjq.planet.common.enums.blog.VisitTypeEnum;

/**
 * @author: gjq0117
 * @date: 2024/4/30 17:53
 * @description: 访问信息构造器
 */
public class VisitorBuilder {

    /**
     * 构建Visitor实体
     *
     * @param ipDetail
     * @param uid
     * @param resourceId
     * @param resourceType
     * @return
     * @see VisitTypeEnum  resourceType
     */
    public static Visitor buildVisitor(IpDetail ipDetail, Long uid, Long resourceId, Integer resourceType) {
        return Visitor.builder()
                .uid(uid)
                .nation(ipDetail.getCountry())
                .province(ipDetail.getRegion())
                .city(ipDetail.getCity())
                .ip(ipDetail.getIp())
                .resourceType(resourceType)
                .resourceId(resourceId)
                .build();
    }
}
