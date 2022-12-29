/*
 Navicat Premium Data Transfer

 Source Server         : ql-time-8.130.101.229
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 8.130.101.229:3306
 Source Schema         : qltime

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 23/06/2022 00:06:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_comments
-- ----------------------------
DROP TABLE IF EXISTS `tb_comments`;
CREATE TABLE `tb_comments` (
  `id` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `userId` int DEFAULT NULL,
  `topId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `isDelete` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `replayUserId` int DEFAULT NULL,
  `lastId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tb_gallery
-- ----------------------------
DROP TABLE IF EXISTS `tb_gallery`;
CREATE TABLE `tb_gallery` (
  `id` varchar(255) NOT NULL,
  `filesize` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `format` varchar(255) DEFAULT NULL,
  `imageHeight` int DEFAULT NULL,
  `imageWidth` int DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `uploadDate` date DEFAULT NULL,
  `fileName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `userId` int DEFAULT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `topId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tb_loveText
-- ----------------------------
DROP TABLE IF EXISTS `tb_loveText`;
CREATE TABLE `tb_loveText` (
  `id` int NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `count` int DEFAULT NULL,
  `length` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
  `id` varchar(255) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `isDelete` varchar(255) DEFAULT NULL,
  `created` date DEFAULT NULL,
  `updated` date DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tb_note
-- ----------------------------
DROP TABLE IF EXISTS `tb_note`;
CREATE TABLE `tb_note` (
  `id` int NOT NULL AUTO_INCREMENT,
  `context` varchar(255) DEFAULT NULL,
  `isDelete` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0',
  `isComplete` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0',
  `userId` int DEFAULT NULL,
  `date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tb_tops
-- ----------------------------
DROP TABLE IF EXISTS `tb_tops`;
CREATE TABLE `tb_tops` (
  `id` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  `zan` int DEFAULT NULL,
  `userId` int DEFAULT NULL,
  `isDelete` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `created` date DEFAULT NULL,
  `updated` date DEFAULT NULL,
  `onlieTime` date DEFAULT NULL,
  `isDelete` int DEFAULT NULL,
  `openId` varchar(255) DEFAULT NULL,
  `sessionKey` varchar(255) DEFAULT NULL,
  `avatarUrl` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `sceneCode` varchar(255) DEFAULT NULL,
  `helfId` int DEFAULT NULL,
  `togetheTime` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tb_weight
-- ----------------------------
DROP TABLE IF EXISTS `tb_weight`;
CREATE TABLE `tb_weight` (
  `id` int NOT NULL,
  `created` date DEFAULT NULL,
  `weight` double(10,2) DEFAULT NULL,
  `userId` int DEFAULT NULL,
  `isDelete` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
