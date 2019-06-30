/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.21-log : Database - cg
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cg` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `cg`;

/*Table structure for table `menu` */

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

/*Table structure for table `order_info` */

DROP TABLE IF EXISTS `order_info`;

CREATE TABLE `order_info` (
  `id` bigint(64) NOT NULL,
  `product_id` bigint(64) NOT NULL,
  `product_type_id` bigint(64) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `pay_time` datetime NOT NULL,
  `pay_type` int(11) NOT NULL,
  `pay_amount` decimal(11,2) NOT NULL COMMENT '支付金额',
  `pay_status` int(11) NOT NULL COMMENT '1:未支付,2:已支付',
  `coupon_amount` decimal(11,2) DEFAULT NULL COMMENT '优惠金额',
  `order_amount` decimal(11,2) DEFAULT NULL COMMENT '订单总额=支付金额+优惠金额',
  `user_id` bigint(64) DEFAULT NULL COMMENT '用户ID',
  `refund_amount` decimal(64,0) DEFAULT NULL COMMENT '退款金额',
  `product_num` int(11) DEFAULT NULL COMMENT '商品数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` bigint(64) NOT NULL,
  `product_type_id` bigint(64) NOT NULL,
  `product_name` varchar(50) NOT NULL,
  `product_desc` varchar(100) DEFAULT NULL,
  `price` decimal(11,2) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` bigint(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` bigint(64) DEFAULT NULL,
  `shop_id` bigint(64) DEFAULT NULL,
  `image_url` varchar(200) DEFAULT NULL,
  `is_delete` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `product_type` */

DROP TABLE IF EXISTS `product_type`;

CREATE TABLE `product_type` (
  `id` bigint(64) NOT NULL,
  `product_type_name` varchar(30) NOT NULL,
  `product_type_desc` varchar(100) DEFAULT NULL,
  `is_delete` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `role` */

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

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(64) NOT NULL,
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `nick_name` varchar(30) NOT NULL COMMENT '昵称',
  `password` varchar(64) NOT NULL,
  `amount` decimal(11,2) DEFAULT NULL,
  `status` int(255) DEFAULT NULL COMMENT '1:正常, 2:禁用',
  `is_delete` int(1) DEFAULT NULL COMMENT '1:删除,0:未删除',
  `sex` int(11) DEFAULT NULL,
  `mobile` varchar(11) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `id_card` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` bigint(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` bigint(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
