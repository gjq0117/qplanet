package com.gjq.planet.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: gjq0117
 * @date: 2024/4/14 11:15
 * @description: 系统角色枚举
 */
@AllArgsConstructor
@Getter
public enum SystemRoleEnum {

    SUPER_ADMIN(0, "admin/站长"),
    ADMIN(1, "管理人员"),
    CUSTOMER(2, "用户");

    private Integer code;
    private String desc;

}
