/*
 Navicat Premium Data Transfer

 Source Server         : 120.79.65.156
 Source Server Type    : MySQL
 Source Server Version : 50643
 Source Host           : 120.79.65.156
 Source Database       : cg

 Target Server Type    : MySQL
 Target Server Version : 50643
 File Encoding         : utf-8

 Date: 06/21/2019 15:28:29 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL,
  `menu_name` varchar(30) NOT NULL,
  `menu_url` varchar(100) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `menu_desc` varchar(30) DEFAULT NULL,
  `is_delete` int(11) NOT NULL COMMENT '1:已删除,0:正常',
  `status` int(11) NOT NULL COMMENT '1:正常, 2:禁用',
  `auth_code` varchar(50) DEFAULT NULL COMMENT '权限编码',
  `create_time` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(30) DEFAULT NULL COMMENT '角色描述',
  `status` int(11) DEFAULT NULL COMMENT '1:正常,2:禁用',
  `is_delete` int(11) DEFAULT NULL COMMENT '1:删除',
  `create_time` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(64) NOT NULL,
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `nick_name` varchar(30) NOT NULL COMMENT '昵称',
  `password` varchar(64) NOT NULL,
  `amount` decimal(11,2) DEFAULT NULL,
  `status` int(255) DEFAULT NULL COMMENT '1:正常, 2:禁用',
  `is_delete` int(11) DEFAULT NULL COMMENT '1:删除,0:未删除',
  `create_time` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
