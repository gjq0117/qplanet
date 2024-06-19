package com.gjq.planet.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gjq.planet.common.domain.entity.Contact;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会话信息表 Mapper 接口
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
public interface ContactMapper extends BaseMapper<Contact> {

    /**
     *  获取群会话列表游标分页信息
     *
     * @param uid 用户ID
     * @param timeCursor 时间游标
     * @return
     */
    List<Contact> getGroupContact(@Param("uid") Long uid, @Param("timeCursor") Date timeCursor);
}
