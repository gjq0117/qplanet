package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.WebInfoMapper;
import com.gjq.planet.common.domain.entity.WebInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 网站信息 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Service
public class WebInfoDao extends ServiceImpl<WebInfoMapper, WebInfo> {

    public WebInfo getFirstOne() {

        return lambdaQuery()
                .orderByDesc(WebInfo::getId)
                .last("limit 1")
                .one();
    }
}
