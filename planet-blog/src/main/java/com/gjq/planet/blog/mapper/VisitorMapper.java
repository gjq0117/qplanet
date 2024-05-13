package com.gjq.planet.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gjq.planet.common.domain.entity.Visitor;
import com.gjq.planet.common.domain.vo.resp.statistics.WebRecentVisitResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 网站访问信息 Mapper 接口
 * </p>
 *
 * @author gjq
 * @since 2024-04-30
 */
@Mapper
public interface VisitorMapper extends BaseMapper<Visitor> {

    /**
     *  查询近七日的网站访问数据
     *
     * @return
     */
    List<WebRecentVisitResp> sevenDayVisitCount();
}
