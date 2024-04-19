package com.gjq.planet.blog.dao;

import com.gjq.planet.blog.mapper.ArticleRelationMapper;import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.common.domain.entity.ArticleRelation;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章多关联外键表 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Service
public class ArticleRelationDao extends ServiceImpl<ArticleRelationMapper, ArticleRelation>  {

}
