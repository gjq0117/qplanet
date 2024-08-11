package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.UserBackpackMapper;
import com.gjq.planet.common.domain.entity.ItemConfig;
import com.gjq.planet.common.domain.entity.UserBackpack;
import com.gjq.planet.common.domain.vo.req.CursorPageBaseReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户背包表 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-06-28
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> {

    @Autowired
    private UserBackpackMapper userBackpackMapper;

    public UserBackpack getItemByUidAndItemId(Long uid, Long itemId) {
        return lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemId)
                .one();
    }

    /**
     * 分页查询物品信息
     *
     * @param req    分页请求
     * @param uid    用户ID
     * @param type   物品类型
     * @param status 物品状态
     * @return
     */
    public List<ItemConfig> pageItemConfig(CursorPageBaseReq req, Long uid, Integer type, Integer status) {
        Date cursor = StringUtils.isBlank(req.getCursor()) ? null : new Date(Long.parseLong(req.getCursor()));
        return userBackpackMapper.pageItemConfig(cursor, req.getPageSize(), uid, type, status);
    }
}
