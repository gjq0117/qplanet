-- start  2024.05.18 17:32
CREATE TABLE `user_apply`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid`         bigint(20) NULL COMMENT '申请人ID',
    `type`        tinyint(1) NULL COMMENT '申请类型【0：好友】',
    `target_id`   bigint(20) NULL COMMENT '目标ID',
    `remark`         varchar(255) NULL COMMENT '申请备注消息',
    `status`      tinyint(1) NULL DEFAULT 0 COMMENT '申请状态【0：待审核、1：已同意、2：已拒绝】',
    `read_status` tinyint(1) NULL DEFAULT 0 COMMENT '已读状态【0：未读、1：已读】',
    `create_time` datetime NULL COMMENT '创建时间',
    `update_time` datetime NULL COMMENT '最后更新时间',
    `deleted`     tinyint(1) NULL DEFAULT 0 COMMENT '是否删除【0：未删除、1：已删除】',
    PRIMARY KEY (`id`),
    INDEX         `idx_target_type`(`target_id`, `type`)
) COMMENT = '用户申请表';

CREATE TABLE `user_friend`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid`         bigint(20) NULL COMMENT '用户ID',
    `friend_uid`  bigint(20) NULL COMMENT '好友ID',
    `is_care`     tinyint(1) NULL COMMENT '是否特别关心【0：否、1：是】',
    `remark`      varchar(32) NULL COMMENT '备注',
    `create_time` datetime NULL COMMENT '创建时间',
    `update_time` datetime NULL COMMENT '最后更新时间',
    `deleted`     tinyint(1) NULL DEFAULT 0 COMMENT '是否删除【0：未删除、1：删除】',
    PRIMARY KEY (`id`),
    INDEX         `idx_uid_friend_uid`(`uid`, `friend_uid`)
) COMMENT = '用户好友表';
-- end

-- start 2024.05.24 start

CREATE TABLE `room`
(
    `id`          bigint(20) NOT NULL COMMENT 'ID',
    `type`        tinyint(1) NULL COMMENT '类型【1：单聊；2：普通群聊；3：全员群聊】',
    `hot_flag`    tinyint(1) NULL COMMENT '是否是热点群聊【0：否；1：是】',
    `active_time` datetime NULL COMMENT '群最后的消息时间',
    `last_msg_id` bigint(20) NULL COMMENT '最后一条消息的ID',
    `extra`       json NULL COMMENT '扩展消息',
    `create_time` datetime NULL COMMENT '创建时间',
    `update_time` datetime NULL COMMENT '更新时间',
    `deleted`     tinyint(1) NULL DEFAULT 0 COMMENT '是否删除【0：未删除；1：删除】',
    PRIMARY KEY (`id`)
) COMMENT = '房间信息表';
insert into room(id, type, hot_flag, create_time, update_time, deleted)
values (1, 3, 1, NOW(), NOW(), 0);

CREATE TABLE `message`
(
    `id`           bigint(20) NOT NULL COMMENT 'ID',
    `room_id`      bigint(20) NULL COMMENT '房间ID',
    `from_uid`     bigint(20) NULL COMMENT '发送者ID',
    `content`      varchar(1204) NULL COMMENT '消息内容',
    `reply_msg_id` bigint(20) NULL COMMENT '回复消息的ID',
    `skip_count`   int(11) NULL COMMENT '跟回复的消息间隔了多少条消息',
    `read_count`   int(11) NULL COMMENT '已读人数',
    `type`         tinyint(1) NULL COMMENT '类型',
    `extra`        json NULL COMMENT '扩展信息',
    `create_time`  datetime NULL COMMENT '创建时间',
    `update_time`  datetime NULL COMMENT '更新时间',
    `deleted`      tinyint(1) NULL DEFAULT 0 COMMENT '是否删除【0：未删除；1：删除】',
    PRIMARY KEY (`id`)
) COMMENT = '消息表';

CREATE TABLE `room_friend`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `room_id`     bigint(20) NULL COMMENT '房间ID',
    `uid1`        bigint(20) NULL COMMENT 'uid1',
    `uid2`        bigint(20) NULL COMMENT 'uid2',
    `room_key`    varchar(64) NULL COMMENT '房间key由两个uid拼接，先做排序uid1_uid2',
    `create_time` datetime NULL COMMENT '创建时间',
    `update_time` datetime NULL COMMENT '更新时间',
    `deleted`     tinyint(1) NULL DEFAULT 0 COMMENT '是否删除【0：未删除；1：删除】',
    PRIMARY KEY (`id`)
) COMMENT = '好友房间';

CREATE TABLE `room_group`
(
    `id`          bigint(20) NOT NULL COMMENT 'ID',
    `room_id`     bigint(20) NULL COMMENT '房间ID',
    `name`        varchar(32) NULL COMMENT '群名',
    `avatar`      varchar(255) NULL COMMENT '群头像',
    `extra`       json NULL COMMENT '扩展信息',
    `create_time` datetime NULL COMMENT '创建时间',
    `update_time` datetime NULL COMMENT '更新时间',
    `deleted`     tinyint(1) NULL DEFAULT 0 COMMENT '是否删除【0：未删除；1：删除】',
    PRIMARY KEY (`id`),
    INDEX         `idx_room_id`(`room_id`)
) COMMENT = '群聊房间';
insert into room_group(id, room_id, name, avatar, create_time, update_time, deleted)
values (1, 1, 'Q星球全员群', 'https://minio.qplanet.cn/planet/user/3ea6beec64369c2642b92c6726f1epng.png', NOW(), NOW(),
        0);

CREATE TABLE `group_member`
(
    `id`          bigint(20) NOT NULL COMMENT '群成员',
    `group_id`    bigint(20) NULL COMMENT '群ID',
    `uid`         bigint(20) NULL COMMENT '用户ID',
    `role`        tinyint(1) NULL COMMENT '角色类型【0：普通成员；1：管理员；2：群主】',
    `create_time` datetime NULL COMMENT '创建时间',
    `update_time` datetime NULL COMMENT '更新时间',
    `deleted`     tinyint(1) NULL DEFAULT 0 COMMENT '是否删除【0：未删除；1：已删除】',
    PRIMARY KEY (`id`)
) COMMENT = '群成员信息';


CREATE TABLE `contact`
(
    `id`          bigint(20) NOT NULL COMMENT 'ID ',
    `uid`         bigint(20) NULL COMMENT '用户ID',
    `room_id`     bigint(20) NULL COMMENT '房间ID',
    `read_time`   datetime NULL COMMENT '阅读到的时间',
    `active_time` datetime NULL COMMENT '会话内消息最后更新的时间(只有普通会话需要维护，全员会话不需要维护)',
    `last_msg_id` bigint(20) NULL COMMENT '最后一条消息ID',
    `create_time` datetime NULL COMMENT '创建时间',
    `update_time` datetime NULL COMMENT '最后更新时间',
    `deleted`     tinyint(1) NULL DEFAULT 0 COMMENT '是否删除【0：未删除；1：删除】',
    PRIMARY KEY (`id`),
    INDEX         `idx_uid_room_id`(`uid`, `room_id`)
) COMMENT = '会话信息表';
-- end
