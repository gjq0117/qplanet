package com.gjq.planet.blog.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.ArticleMapper;
import com.gjq.planet.common.domain.entity.Article;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章信息 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Service
public class ArticleDao extends ServiceImpl<ArticleMapper, Article> {

}
