package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户申请表
 * </p>
 *
 * @author gjq
 * @since 2024-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_apply")
public class UserApply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 申请人ID
     */
    @TableField("uid")
    private Long uid;

    /**
     * 申请类型【0：好友】
     */
    @TableField("type")
    private Integer type;

    /**
     * 目标ID
     */
    @TableField("target_id")
    private Long targetId;

    /**
     * 申请备注消息
     */
    @TableField("remark")
    private String remark;

    /**
     * 申请状态【0：待审核、1：已同意、2：已拒绝】
     */
    @TableField("status")
    private Integer status;

    /**
     * 已读状态【0：未读、1：已读】
     */
    @TableField("read_status")
    private Integer readStatus;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 是否删除【0：未删除、1：已删除】
     */
    @TableField("deleted")
    private Integer deleted;


}
