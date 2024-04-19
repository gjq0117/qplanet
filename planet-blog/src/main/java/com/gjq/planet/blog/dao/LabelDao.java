package com.gjq.planet.blog.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.LabelMapper;
import com.gjq.planet.common.domain.entity.Label;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签信息 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Service
public class LabelDao extends ServiceImpl<LabelMapper, Label> {

}
