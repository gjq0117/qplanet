package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.dao.ArticleDao;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.dao.VisitorDao;
import com.gjq.planet.blog.service.IVisitorService;
import com.gjq.planet.blog.service.IWebInfoService;
import com.gjq.planet.blog.service.IpService;
import com.gjq.planet.blog.service.adapter.VisitorBuilder;
import com.gjq.planet.common.utils.CommonUtil;
import com.gjq.planet.common.utils.JsonUtils;
import com.gjq.planet.common.utils.RedisUtils;
import com.gjq.planet.common.utils.RequestHolder;
import com.gjq.planet.common.constant.BlogRedisKey;
import com.gjq.planet.common.domain.dto.RequestInfo;
import com.gjq.planet.common.domain.entity.Article;
import com.gjq.planet.common.domain.entity.IpDetail;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.entity.Visitor;
import com.gjq.planet.common.domain.vo.req.visitor.VisitorListReq;
import com.gjq.planet.common.domain.vo.resp.visitor.VisitorListResp;
import com.gjq.planet.common.domain.vo.resp.visitor.VisitorProvinceResp;
import com.gjq.planet.common.enums.blog.VisitTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/30 17:24
 * @description: 访问记录业务
 */
@Service
@Slf4j
public class VisitorServiceImpl implements IVisitorService {

    @Autowired
    private IpService ipService;

    @Autowired
    private VisitorDao visitorDao;

    @Autowired
    private IWebInfoService webInfoService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public void visitWeb() {
        // 访问网站
        RequestInfo requestInfo = RequestHolder.get();
        saveWebVisitInfo(requestInfo.getUid(), Optional.ofNullable(requestInfo.getIp()).orElse("127.0.0.1"));
    }

    @Override
    public void saveWebVisitInfo(Long uid, @NotNull String ip) {
        String json_str = "";
        Visitor visitor = null;
        IpDetail ipDetail = null;
        String redisKey = "";
        if (Objects.nonNull(uid)) {
            // 用户访问
            json_str = RedisUtils.getStr(BlogRedisKey.getKey(BlogRedisKey.VISITOR_UID_INFO, uid));
            if (StringUtils.isNotBlank(json_str)) {
                return;
            }
            ipDetail = ipService.getIpDetail(ip);
            visitor = VisitorBuilder.buildVisitor(ipDetail, uid, null, VisitTypeEnum.BLOG_WEB.getType());
            redisKey = BlogRedisKey.getKey(BlogRedisKey.VISITOR_UID_INFO, uid);
        } else {
            // 匿名访问
            json_str = RedisUtils.getStr(BlogRedisKey.getKey(BlogRedisKey.VISITOR_IP_INFO, ip));
            if (StringUtils.isNotBlank(json_str)) {
                return;
            }
            ipDetail = ipService.getIpDetail(ip);
            visitor = VisitorBuilder.buildVisitor(ipDetail, null, null, VisitTypeEnum.BLOG_WEB.getType());
            redisKey = BlogRedisKey.getKey(BlogRedisKey.VISITOR_IP_INFO, ip);
        }
        if (Objects.nonNull(ipDetail)) {
            // 保存到redis 过期时间为现在到第二天零点的秒数
            RedisUtils.set(redisKey, JsonUtils.toStr(visitor), CommonUtil.getSecondsNextEarlyMorning(), TimeUnit.SECONDS);
            visitorDao.save(visitor);
            // 更新网站访问量
            webInfoService.addVisitCount();
        }
    }



    @Override
    public void saveArticleVisitInfo(Long articleId) {
        IpDetail ipDetail = ipService.getIpDetail(RequestHolder.get().getIp());
        if (Objects.nonNull(ipDetail)) {
            Visitor visitor = VisitorBuilder.buildVisitor(ipDetail, RequestHolder.get().getUid(), articleId, VisitTypeEnum.BOLG_ARTICLE.getType());
            visitorDao.save(visitor);
        }
    }

    @Override
    public List<VisitorProvinceResp> getProvinceList() {
        return visitorDao.list().stream().map(visitor ->
                VisitorProvinceResp.builder().name("XX".equals(visitor.getProvince()) ? "本地" : visitor.getProvince()).build()
        ).distinct().collect(Collectors.toList());
    }

    @Override
    public List<VisitorListResp> getVisitorList(VisitorListReq req) {
        List<Visitor> visitorList = visitorDao.list();
        List<VisitorListResp> result = visitorList.stream().map(visitor -> {
            VisitorListResp resp = new VisitorListResp();
            BeanUtils.copyProperties(visitor, resp);
            // 封装用户信息
            CompletableFuture<User> userFuture = CompletableFuture.supplyAsync(() ->
                            userDao.getById(visitor.getUid())
                    , executor);

            // 封装资源信息
            if (VisitTypeEnum.BLOG_WEB.getType().equals(visitor.getResourceType())) {
                // 博客网
            } else if (VisitTypeEnum.BOLG_ARTICLE.getType().equals(visitor.getResourceType())) {
                // 博客文章
                try {
                    Article article = CompletableFuture.supplyAsync(() ->
                                    articleDao.getById(visitor.getResourceId())
                            , executor).get();
                    // 文章标题
                    resp.setResourceName(article.getArticleTitle());
                } catch (Exception e) {
                    log.error("获取访问者列表解析文章资源时候出错,文章ID：{},错误信息：{}", visitor.getResourceId(), e);
                    e.printStackTrace();
                }
            }
            try {
                User user = userFuture.get();
                if (Objects.nonNull(user)) {
                    resp.setUsername(user.getUsername());
                }
            } catch (Exception e) {
                log.error("获取访问者列表解析用户资源时候出错,用户ID：{},错误信息：{}", visitor.getUid(), e);
                e.printStackTrace();
            }
            return resp;
        }).sorted(Comparator.comparing(VisitorListResp::getCreateTime).reversed()).collect(Collectors.toList());
        if (!visitListReqIsNull(req)) {
            if (StringUtils.isNotBlank(req.getProvince()) && "本地".equals(req.getProvince())) {
                req.setProvince("XX");
            }
            // 过滤
            result = result.stream().filter(resp ->
                    (StringUtils.isBlank(req.getProvince()) || req.getProvince().equals(resp.getProvince())) &&
                            (StringUtils.isBlank(req.getUsername()) || req.getUsername().equals(resp.getUsername())) &&
                            (Objects.isNull(req.getType()) || req.getType().equals(resp.getResourceType()))
            ).collect(Collectors.toList());
        }
        return result;
    }

    /**
     * 判断访问信息请求是否为空
     *
     * @param req
     * @return
     */
    private boolean visitListReqIsNull(VisitorListReq req) {
        if (Objects.isNull(req)) {
            return true;
        }
        return StringUtils.isBlank(req.getProvince()) &&
                Objects.isNull(req.getType()) &&
                StringUtils.isBlank(req.getUsername());
    }
}
