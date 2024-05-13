package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.SortMapper;
import com.gjq.planet.common.domain.entity.Sort;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分类信息 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Service
public class SortDao extends ServiceImpl<SortMapper, Sort> {
}
