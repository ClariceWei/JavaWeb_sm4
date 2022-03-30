/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : sm4

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 03/05/2021 15:59:41
*/

SET NAMES utf8mb4;
create DATABASE sm4;
use sm4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for checking
-- ----------------------------
DROP TABLE IF EXISTS `checking`;
CREATE TABLE `checking`  (
  `id` tinyint(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `isCheckd` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `checkDo` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  CONSTRAINT `checking_ibfk_1` FOREIGN KEY (`id`) REFERENCES `state` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pass
-- ----------------------------
DROP TABLE IF EXISTS `pass`;
CREATE TABLE `pass`  (
  `id` tinyint(3) NOT NULL AUTO_INCREMENT,
  `pass_cipher` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone_cipher` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `passRan` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  CONSTRAINT `pass_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for state
-- ----------------------------
DROP TABLE IF EXISTS `state`;
CREATE TABLE `state`  (
  `id` tinyint(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `addr_cipher` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone_cipher` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `clock` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `temper` double NOT NULL,
  `goArea` int(3) NOT NULL,
  `state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `infoRan` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  INDEX `state_ibfk_1`(`name`) USING BTREE,
  CONSTRAINT `state_ibfk_1` FOREIGN KEY (`name`) REFERENCES `user` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for thekey
-- ----------------------------
DROP TABLE IF EXISTS `theKey`;
CREATE TABLE `theKey`  (
  `id` tinyint(3) NOT NULL AUTO_INCREMENT,
  `mainKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` tinyint(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` int(1) UNSIGNED ZEROFILL NOT NULL,
  PRIMARY KEY (`id`, `name`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
