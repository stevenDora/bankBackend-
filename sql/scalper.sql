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

 Date: 18/06/2019 13:23:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for scalper
-- ----------------------------
DROP TABLE IF EXISTS `scalper`;
CREATE TABLE `scalper`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scalper_id` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接受推送派单的唯一金主id',
  `root_id` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '根代理id',
  `upper_id` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '直接代理id',
  `agent_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代理层级关系路径',
  `avaliable_balance` decimal(12, 2) NULL DEFAULT NULL COMMENT '可用余额',
  `freeze_balance` decimal(12, 2) NULL DEFAULT NULL COMMENT '冻结余额',
  `total_balance` decimal(12, 2) NULL DEFAULT NULL COMMENT '总额',
  `last_assign_task_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后一次派单时间',
  `last_account_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后一次到账时间',
  `last_charge_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后一次充值时间',
  `last_flow_push_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后一次信息流水推送时间',
  `alipay_switch` tinyint(4) NULL DEFAULT NULL COMMENT '金主支付宝开关',
  `wx_switch` tinyint(4) NULL DEFAULT NULL COMMENT '金主微信开关',
  `bank_switch` tinyint(4) NULL DEFAULT NULL COMMENT '金主银行卡开关',
  `block_status` tinyint(4) NULL DEFAULT NULL COMMENT '运营总控',
  `alipay_rate` decimal(12, 2) NULL DEFAULT NULL COMMENT '金主支付宝费率',
  `wx_rate` decimal(12, 2) NULL DEFAULT NULL COMMENT '金主微信费率',
  `bank_rate` decimal(12, 2) NULL DEFAULT NULL COMMENT '金主银行卡费率',
  `crt_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `upt_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
