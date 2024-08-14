package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.dao.ArticleDao;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.dao.WebInfoDao;
import com.gjq.planet.blog.mapper.UserMapper;
import com.gjq.planet.blog.mapper.VisitorMapper;
import com.gjq.planet.blog.service.StatisticsService;
import com.gjq.planet.common.domain.entity.WebInfo;
import com.gjq.planet.common.domain.vo.resp.statistics.GenderRateResp;
import com.gjq.planet.common.domain.vo.resp.statistics.KeyBroadResp;
import com.gjq.planet.common.domain.vo.resp.statistics.WebRecentVisitResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author: gjq0117
 * @date: 2024/5/3 18:09
 * @description:
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private WebInfoDao webInfoDao;

    @Autowired
    private VisitorMapper visitorMapper;

    @Override
    public KeyBroadResp getKeyBroadData() {
        return KeyBroadResp.builder()
                .userCount((int)userDao.count())
                .articleCount(articleDao.getPublishCount())
                .viewCount(Optional.ofNullable(webInfoDao.getFirstOne()).map(WebInfo::getViewCount).orElse(0L))
                // TODO 点赞数量先写死
                .likeCount(0)
                .build();
    }

    @Override
    public List<WebRecentVisitResp> sevenDayVisitCount() {
        return visitorMapper.sevenDayVisitCount();
    }

    @Override
    public List<GenderRateResp> getGenderRate() {
        return userMapper.getGenderRate();
    }
}
