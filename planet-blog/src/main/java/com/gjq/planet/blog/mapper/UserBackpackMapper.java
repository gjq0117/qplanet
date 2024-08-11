package com.gjq.planet.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gjq.planet.common.domain.entity.ItemConfig;
import com.gjq.planet.common.domain.entity.UserBackpack;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户背包表 Mapper 接口
 * </p>
 *
 * @author gjq
 * @since 2024-06-28
 */
public interface UserBackpackMapper extends BaseMapper<UserBackpack> {

    /**
     *  分页查询表情包列表
     *
     * @param cursor
     * @param pageSize
     * @param uid
     * @param type
     * @param status
     * @return
     */
    List<ItemConfig> pageItemConfig(Date cursor, Integer pageSize, @Param("uid") Long uid, @Param("type") Integer type, @Param("status") Integer status);
}
