package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户背包表
 * </p>
 *
 * @author gjq
 * @since 2024-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_backpack")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBackpack implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("uid")
    private Long uid;

    /**
     * 物品ID
     */
    @TableField("item_id")
    private Long itemId;

    /**
     * 是否有效【0：无效，1：有效】
     * @see com.gjq.planet.common.enums.common.ItemStatusEnum
     */
    @TableField("status")
    private Integer status;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 是否删除【0：未删除；1：删除】
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


}
