package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.vo.resp.statistics.GenderRateResp;
import com.gjq.planet.common.domain.vo.resp.statistics.KeyBroadResp;
import com.gjq.planet.common.domain.vo.resp.statistics.WebRecentVisitResp;

import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/5/3 18:09
 * @description: 统计接口
 */
public interface StatisticsService {
    /**
     *  获取统计关键数据
     *
     * @return
     */
    KeyBroadResp getKeyBroadData();

    /**
     *  查询网站近七日访问量
     *
     * @return
     */
    List<WebRecentVisitResp> sevenDayVisitCount();

    /**
     *  获取网站用户男女比例
     *
     * @return
     */
    List<GenderRateResp> getGenderRate();
}
