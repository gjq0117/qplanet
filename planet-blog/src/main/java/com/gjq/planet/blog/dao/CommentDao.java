package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.CommentMapper;
import com.gjq.planet.common.domain.entity.Comment;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论信息 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Service
public class CommentDao extends ServiceImpl<CommentMapper, Comment> {

}
