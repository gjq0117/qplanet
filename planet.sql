/*
 Navicat Premium Data Transfer

 Source Server         : 175.178.116.84（腾讯云）
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 175.178.116.84:7110
 Source Schema         : planet

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 04/05/2024 18:01:00
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`          bigint(20) NULL DEFAULT NULL COMMENT '作者ID',
    `sort_id`          bigint(20) NULL DEFAULT NULL COMMENT '分类ID',
    `article_cover`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章封面',
    `article_title`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章标题',
    `article_content`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容',
    `video_url`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频链接',
    `view_count`       bigint(20) NULL DEFAULT NULL COMMENT '浏览量',
    `like_count`       bigint(20) NULL DEFAULT NULL COMMENT '点赞数',
    `comment_count`    bigint(20) NULL DEFAULT NULL COMMENT '评论数',
    `view_status`      tinyint(1) NULL DEFAULT NULL COMMENT '是否可见【0：否；1：是】',
    `recommend_status` tinyint(1) NULL DEFAULT NULL COMMENT '是否推荐【0：否；1：是】',
    `comment_status`   tinyint(1) NULL DEFAULT NULL COMMENT '是否启用评论【0：否；1：是】',
    `status`           tinyint(1) NULL DEFAULT NULL COMMENT '文章状态【0：保存；1：发布；2：下架】',
    `publish_time`     datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
    `create_time`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`      datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
    `deleted`          tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '是否删除【0：未删除；1：删除】',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_relation
-- ----------------------------
DROP TABLE IF EXISTS `article_relation`;
CREATE TABLE `article_relation`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `article_id`  bigint(20) NULL DEFAULT NULL COMMENT '文章ID',
    `target_id`   bigint(20) NULL DEFAULT NULL COMMENT '关联目标ID',
    `type`        tinyint(1) NULL DEFAULT NULL COMMENT '类型：【0：标签；】',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
    `deleted`     tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '是否删除【0：未删除；1：删除】',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章多关联外键表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`
(
    `id`              bigint(20) NOT NULL COMMENT '外键',
    `user_id`         bigint(20) NULL DEFAULT NULL COMMENT '发表用户ID',
    `target_id`       bigint(20) NULL DEFAULT NULL COMMENT '评论来源目标ID',
    `type`            tinyint(1) NULL DEFAULT NULL COMMENT '评论来源类型【0：文章；】',
    `floor_id`        bigint(20) NULL DEFAULT NULL COMMENT '楼层ID',
    `parent_id`       bigint(20) NULL DEFAULT NULL COMMENT '父评论ID',
    `parent_user_id`  bigint(20) NULL DEFAULT NULL COMMENT '父发表用户ID',
    `comment_content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论内容',
    `like_count`      bigint(20) NULL DEFAULT NULL COMMENT '评论点赞数',
    `create_time`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
    `deleted`         tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '是否删除【0：未删除；1：删除】',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `sort_id`           bigint(20) NULL DEFAULT NULL COMMENT '分类ID',
    `label_name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签名',
    `label_description` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签描述',
    `create_time`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`       datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
    `deleted`           tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '是否删除【0：未删除；1：删除】',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sort
-- ----------------------------
DROP TABLE IF EXISTS `sort`;
CREATE TABLE `sort`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `sort_name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名',
    `sort_description` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类描述',
    `sort_img`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '背景图片',
    `motto`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '格言',
    `priority`         int(5) NULL DEFAULT NULL COMMENT '优先级：数字小的在前面',
    `create_time`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`      datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
    `deleted`          tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '是否删除【0：未删除 1：删除】',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分类信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `nickname`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
    `password`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
    `email`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `phone`            varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
    `gender`           tinyint(1) NULL DEFAULT NULL COMMENT '性别【0:保密；1:男；2:女】',
    `user_status`      tinyint(1) NULL DEFAULT NULL COMMENT '是否启用【0：否；1：是】',
    `open_id`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'openId',
    `avatar`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
    `introduction`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
    `ip_info`          json NULL COMMENT 'ip信息',
    `is_active`        tinyint(1) NULL DEFAULT NULL COMMENT '是否在线【0：否；1：是】',
    `last_active_time` datetime(0) NULL DEFAULT NULL COMMENT '最后的上线时间',
    `user_type`        tinyint(1) NULL DEFAULT NULL COMMENT '用户类型【0：admin；1：管理员；2：普通用户】',
    `create_time`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`      datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
    `deleted`          tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '是否删除【0：未删除；1：删除】',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for visitor
-- ----------------------------
DROP TABLE IF EXISTS `visitor`;
CREATE TABLE `visitor`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `uid`           bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
    `ip`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'IP',
    `nation`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家',
    `province`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份',
    `city`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市',
    `resource_id`   bigint(20) NULL DEFAULT NULL COMMENT '访问的资源ID',
    `resource_type` int(5) NULL DEFAULT NULL COMMENT '访问的资源类型【0：网站；1：文章】',
    `create_time`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`       tinyint(1) NULL DEFAULT 0 COMMENT '是否删除【0：未删除；1：删除】',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX           `ip_index`(`ip`) USING BTREE COMMENT 'IP索引'
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网站访问信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for web_info
-- ----------------------------
DROP TABLE IF EXISTS `web_info`;
CREATE TABLE `web_info`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `web_name`         varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站名',
    `author_name`      varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者名',
    `mottos`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '格言/座右铭/箴言（可配置多条，用封号间隔）',
    `notices`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公告（可配置多条，用封号间隔）',
    `avatar`           varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
    `background_image` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '背景图',
    `footers`          varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '页脚（可配置多条，用封号间隔）',
    `view_count`       bigint(20) NULL DEFAULT NULL COMMENT '网站访问量',
    `create_time`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`      datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
    `deleted`          tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '是否删除 【0：未删除；1：删除】',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网站信息' ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;

-- 插入管理员（密码：admin）
insert into `user`(username, nickname, `password`, gender, user_status, user_type) VALUE ("admin","超级管理员","$2a$10$cgbd80ZnocCrngiSAg1K3.msFec9VNUVXzeC.c3UzeeM8ZZ9oUrD.",0,1,0)
