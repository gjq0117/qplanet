package com.gjq.planet.blog.service.adapter;

import com.gjq.planet.common.domain.entity.WebInfo;
import com.gjq.planet.common.domain.vo.req.webinfo.WebInfoUpdateReq;
import com.gjq.planet.common.domain.vo.resp.webinfo.WebInfoListItem;
import com.gjq.planet.common.domain.vo.resp.webinfo.WebInfoResp;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/19 12:05
 * @description: webInfo 相关对象构造器
 */
public class WebInfoBuilder {

    /**
     * 列表分割规则
     */
    private static final String LIST_SPLIT_RULE = "\\|";

    /**
     *  根据WebInfoReq构建WebInfo对象
     *
     * @param req
     * @return
     */
    public static WebInfo buildFromReq(WebInfoUpdateReq req) {
        WebInfo result = new WebInfo();
        BeanUtils.copyProperties(req, result);
        return result;
    }

    /**
     * 构建webInfo的响应对象
     *
     * @param webInfo
     * @return
     */
    public static WebInfoResp buildResp(WebInfo webInfo) {
        if (Objects.isNull(webInfo)) {
            return null;
        }
        WebInfoResp result = new WebInfoResp();
        BeanUtils.copyProperties(webInfo, result);
        formatWebInfo(webInfo, result);
        return result;
    }

    /**
     * 格式化webInfoResp 对象
     *
     * @param webInfo
     * @param webInfoResp
     */
    private static void formatWebInfo(WebInfo webInfo, WebInfoResp webInfoResp) {
        webInfoResp.setNoticeList(splitWebInfoList(webInfo.getNotices(), LIST_SPLIT_RULE));
        webInfoResp.setMottoList(splitWebInfoList(webInfo.getMottos(), LIST_SPLIT_RULE));
        webInfoResp.setFooterList(splitWebInfoList(webInfo.getFooters(), LIST_SPLIT_RULE));
    }

    /**
     * 分割字符串并映射成指定对象
     *
     * @param str
     * @param rule
     * @return
     */
    private static List<WebInfoListItem> splitWebInfoList(String str, String rule) {
        return Arrays.asList(str.split(rule)).stream().map(s ->
                WebInfoListItem
                        .builder()
                        .value(s)
                        .build()
        ).collect(Collectors.toList());
    }


}
