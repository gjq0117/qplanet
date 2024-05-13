package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.VisitorMapper;
import com.gjq.planet.common.domain.entity.Visitor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站访问信息 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-04-30
 */
@Service
public class VisitorDao extends ServiceImpl<VisitorMapper, Visitor> {

}
