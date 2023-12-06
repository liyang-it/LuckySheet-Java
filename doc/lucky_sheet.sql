/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50640 (5.6.40-log)
 Source Host           : localhost:3306
 Source Schema         : lucky_sheet

 Target Server Type    : MySQL
 Target Server Version : 50640 (5.6.40-log)
 File Encoding         : 65001

 Date: 05/12/2023 16:21:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_excel_work_sheet
-- ----------------------------
DROP TABLE IF EXISTS `t_excel_work_sheet`;
CREATE TABLE `t_excel_work_sheet`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grid_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'excel表格唯一标识符',
  `sheet_json` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '保存sheet工作表数据, 对应Luckysheet.option.data',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '协同Excel - excel表格 sheet工作表数据 ,数据都是用json字符串存储，因为动态字段太多了，一个一个维护很麻烦' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_excel_work_sheet
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
