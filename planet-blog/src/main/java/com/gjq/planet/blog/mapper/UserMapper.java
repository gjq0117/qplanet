package com.gjq.planet.blog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.vo.req.user.UserListReq;
import com.gjq.planet.common.domain.vo.resp.statistics.GenderRateResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     *  条件查询用户列表
     *
     * @param req
     * @return
     */
    List<User> searchUserList(UserListReq req);

    /**
     *  查询系统用户性别比例
     *
     * @return
     */
    List<GenderRateResp> getGenderRate();
}
