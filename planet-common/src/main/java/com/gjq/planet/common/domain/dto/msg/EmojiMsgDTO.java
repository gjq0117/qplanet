package com.gjq.planet.common.domain.dto.msg;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author: gjq0117
 * @date: 2024/5/29 22:26
 * @description: 表情包消息
 */
@SuperBuilder
@NoArgsConstructor
public class EmojiMsgDTO extends BaseFileMsgDTO implements Serializable {
    private static final long serialVersionUID = 1L;
}
