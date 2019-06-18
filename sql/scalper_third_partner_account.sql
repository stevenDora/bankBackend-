/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : guns

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 18/06/2019 13:27:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for scalper_third_partner_account
-- ----------------------------
DROP TABLE IF EXISTS `scalper_third_partner_account`;
CREATE TABLE `scalper_third_partner_account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `scalper_id` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '金主id',
  `channel` tinyint(4) NULL DEFAULT NULL COMMENT '第三方渠道',
  `third_parter_id` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付宝或者微信账号',
  `third_parter_qr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付宝或者微信二维码',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '激活状态 0:未激活 1:激活',
  `crt_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `upt_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
