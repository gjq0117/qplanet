package com.gjq.planet.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: gjq0117
 * @date: 2024/4/13 17:44
 * @description: 0 : no  1: yes
 */
@AllArgsConstructor
@Getter
public enum YesOrNoEnum {

    YES(1, "是"),
    NO(0, "否")
    ;

    private Integer code;
    private String desc;


}
