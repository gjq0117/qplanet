package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.UserApplyMapper;
import com.gjq.planet.common.domain.entity.UserApply;
import com.gjq.planet.common.domain.vo.req.userapply.UserApplyPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.enums.im.ApplyStatusEnum;
import com.gjq.planet.common.enums.im.UserApplyTypeEnum;
import com.gjq.planet.common.utils.CursorUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户申请表 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-05-18
 */
@Service
public class UserApplyDao extends ServiceImpl<UserApplyMapper, UserApply> {

    /**
     * 获取待审核申请记录
     *
     * @param applyUid  申请人id
     * @param applyType 申请类型
     * @param targetId  目标id
     * @return record
     */
    public UserApply getUnReviewApplyRecord(Long applyUid, Integer applyType, Long targetId) {
        return lambdaQuery()
                .eq(UserApply::getTargetId, targetId)
                .eq(UserApply::getType, applyType)
                .eq(UserApply::getUid, applyUid)
                .eq(UserApply::getStatus, ApplyStatusEnum.PENDING_REVIEW.getStatus())
                .one();
    }

    /**
     * 通过目标Id查找申请记录
     *
     * @return List<UserApply>
     */
    public CursorPageBaseResp<UserApply> pageFriendApplyByTargetId(UserApplyPageReq req) {
        return CursorUtils.getCursorPageByMysql(this, req, wrapper -> {
             wrapper.eq(UserApply::getTargetId, req.getCurrUid())
                     .eq(UserApply::getType, UserApplyTypeEnum.FRIEND_APPLY.getType());
        }, UserApply::getCreateTime);

    }

    /**
     * 改变申请状态
     *
     * @param targetId  目标ID
     * @param applyType 申请类型
     * @param applyUid  申请用户ID
     * @param status    状态
     * @return 是否成功
     */
    public Boolean changeApplyStatus(Long targetId, Integer applyType, Long applyUid, Integer status) {
        return lambdaUpdate()
                .set(UserApply::getStatus, status)
                .eq(UserApply::getTargetId, targetId)
                .eq(UserApply::getType, applyType)
                .eq(UserApply::getUid, applyUid)
                .update();
    }
}
