package com.gjq.planet.blog.service.impl;

import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.service.IpService;
import com.gjq.planet.blog.service.adapter.IpInfoBuilder;
import com.gjq.planet.blog.utils.ApiResult;
import com.gjq.planet.blog.utils.JsonUtils;
import com.gjq.planet.common.domain.entity.IpDetail;
import com.gjq.planet.common.domain.entity.IpInfo;
import com.gjq.planet.common.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: gjq0117
 * @date: 2024/4/15 11:00
 * @description: 使用淘宝开发接口获取ip信息, 但是这个接口不稳定，频繁请求容易报错。为了不影响系统正常运行，采用异步的方式请求此接口
 */
@Service
@Slf4j
public class IpServiceImpl implements IpService {


    /**
     * 解析失败后重试次数
     */
    public static final int TRY_TIMES = 3;


    /**
     * 定义一个处理ip解析的线程池
     */
    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(500), new NamedThreadFactory("refresh-ipInfo", false));


    @Autowired
    private UserDao userDao;


    @Override
    public void refreshIpInfoAsync(Long uid,String ip) {
        User user = userDao.getById(uid);
        EXECUTOR.execute(() -> {
            updateIpInfo(user, ip);
        });
    }

    private void updateIpInfo(User user, String ip) {
        IpInfo ipInfo = user.getIpInfo();
        IpDetail ipDetail = null;
        boolean updateFlag = false;
        if (Objects.isNull(ipInfo)) {
            // 考虑用户创建时的情况
            ipDetail = tryGetIpDetailOrNullThreeTimes(ip);
            ipInfo = IpInfoBuilder.buildIpInfo(ipDetail);
            updateFlag = true;
        } else if (ipInfo.needRefreshIpDetail(ip)) {
            // 考虑登录时的情况
            ipDetail = tryGetIpDetailOrNullThreeTimes(ip);
            ipInfo.setUpdateIp(ip);
            ipInfo.setUpdateIpDetail(ipDetail);
            updateFlag = true;
        }
        if (updateFlag) {
            User update = User.builder()
                    .id(user.getId())
                    .ipInfo(ipInfo)
                    .build();
            userDao.updateById(update);
        }
    }

    /**
     * 尝试获取ip信息，失败了可以重试，最多重试三次
     *
     * @param ip
     * @return
     */
    private static IpDetail tryGetIpDetailOrNullThreeTimes(String ip) {
        for (int i = 0; i < TRY_TIMES; i++) {
            IpDetail ipDetail = getIpDetailOrNull(ip);
            if (Objects.nonNull(ipDetail)) {
                return ipDetail;
            }
            try {
                // 失败后休息两秒继续重试
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("tryGetIpDetailOrNullTreeTimes InterruptedException", e);
            }
        }
        return null;
    }


    /**
     * 获取ip信息，如果没有获取到就返回空
     *
     * @param ip
     * @return
     */
    private static IpDetail getIpDetailOrNull(String ip) {
        IpDetail ipDetail = null;
        String url = "https://ip.taobao.com/outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc";
        try {
            String data = HttpUtil.get(url);
            ApiResult<IpDetail> result = JsonUtils.toObj(data, new TypeReference<ApiResult<IpDetail>>() {
            });
            ipDetail = result.getData();
            return ipDetail;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        Date begin = new Date();
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            EXECUTOR.execute(() -> {
                IpDetail ipDetail = tryGetIpDetailOrNullThreeTimes("36.148.194.243");
                if (Objects.nonNull(ipDetail)) {
                    Date date = new Date();
                    System.out.println(String.format("第%d次成功，目前用时%dms", finalI, date.getTime() - begin.getTime()));
                    System.out.println("=====" + ipDetail);
                }
            });
        }
    }
}
