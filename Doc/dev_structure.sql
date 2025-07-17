/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : dev

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 05/07/2025 11:12:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `ActivityID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ActName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `StartAt` datetime NOT NULL,
  `EndAt` datetime NOT NULL,
  `Description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1',
  `isReward` tinyint(1) NOT NULL DEFAULT 0,
  `TotalProgress` int NULL DEFAULT NULL,
  `Level` int NULL DEFAULT NULL,
  PRIMARY KEY (`ActivityID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES ('1', '测试', '2025-07-03 15:19:23', '2025-07-18 15:19:27', '完成LEVEL1', 1, 2, 1);
INSERT INTO `activity` VALUES ('2', 'ceshi22', '2025-07-03 15:20:26', '2025-07-17 15:20:29', '完成LEVEL2', 0, 2, 2);
INSERT INTO `activity` VALUES ('3', '新手福利', '2025-07-01 17:58:38', '2025-07-17 17:58:42', '注册可领', 0, 0, 0);
-- ----------------------------
-- Table structure for balancelog
-- ----------------------------
DROP TABLE IF EXISTS `balancelog`;
CREATE TABLE `balancelog`  (
  `LogID` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ChangeType` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Amount` decimal(12, 2) NOT NULL,
  `BalanceBefore` decimal(12, 2) NOT NULL,
  `BalanceAfter` decimal(12, 2) NOT NULL,
  `OccurredAt` datetime NOT NULL,
  PRIMARY KEY (`LogID`) USING BTREE,
  INDEX `Username`(`Username` ASC) USING BTREE,
  CONSTRAINT `balancelog_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `users` (`Username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for fortest
-- ----------------------------
DROP TABLE IF EXISTS `fortest`;
CREATE TABLE `fortest`  (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for gamerecords
-- ----------------------------
DROP TABLE IF EXISTS `gamerecords`;
CREATE TABLE `gamerecords`  (
  `UserName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `PlayTime` datetime NOT NULL,
  `Level` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `costTime` float NOT NULL,
  `isMaximumUpdate` tinyint(1) NOT NULL DEFAULT 0,
  `RecordID` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`RecordID`) USING BTREE,
  INDEX `IX_GameRecords_User_PlayTime`(`UserName` ASC, `PlayTime` DESC) USING BTREE,
  CONSTRAINT `gamerecords_ibfk_1` FOREIGN KEY (`UserName`) REFERENCES `users` (`Username`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `ItemID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ItemName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ItemPrice` decimal(12, 2) NOT NULL DEFAULT 0.00,
  `CurrentDiscount` decimal(2, 2) NOT NULL DEFAULT 0.00,
  `isDuplicate` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`ItemID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('1', '复活币', 2.00, 0.20, 1);
INSERT INTO `item` VALUES ('2', '包过卡', 10.00, 0.40, 1);
INSERT INTO `item` VALUES ('999', '余额', 1.00, 0.00, 1);
-- ----------------------------
-- Table structure for itemlog
-- ----------------------------
DROP TABLE IF EXISTS `itemlog`;
CREATE TABLE `itemlog`  (
  `LogID` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ItemID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `DeltaQty` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Reason` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `OccurredAt` datetime NOT NULL,
  PRIMARY KEY (`LogID`) USING BTREE,
  INDEX `Username`(`Username` ASC) USING BTREE,
  INDEX `ItemID`(`ItemID` ASC) USING BTREE,
  CONSTRAINT `itemlog_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `users` (`Username`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `itemlog_ibfk_2` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for loginlog
-- ----------------------------
DROP TABLE IF EXISTS `loginlog`;
CREATE TABLE `loginlog`  (
  `LogID` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `IPAddress` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `OccurredAt` datetime NOT NULL,
  PRIMARY KEY (`LogID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for participation
-- ----------------------------
DROP TABLE IF EXISTS `participation`;
CREATE TABLE `participation`  (
  `ActivityID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `UserName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CurrentProgress` int NOT NULL,
  `hasRedeemedReward` tinyint(1) NOT NULL,
  PRIMARY KEY (`ActivityID`, `UserName`) USING BTREE,
  INDEX `UserName`(`UserName` ASC) USING BTREE,
  CONSTRAINT `participation_ibfk_1` FOREIGN KEY (`ActivityID`) REFERENCES `activity` (`ActivityID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `participation_ibfk_2` FOREIGN KEY (`UserName`) REFERENCES `users` (`Username`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for reward
-- ----------------------------
DROP TABLE IF EXISTS `reward`;
CREATE TABLE `reward`  (
  `ActivityID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ItemID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `itemNum` int NOT NULL,
  PRIMARY KEY (`ActivityID`, `ItemID`) USING BTREE,
  INDEX `ItemID`(`ItemID` ASC) USING BTREE,
  CONSTRAINT `reward_ibfk_1` FOREIGN KEY (`ActivityID`) REFERENCES `activity` (`ActivityID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `reward_ibfk_2` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reward
-- ----------------------------
INSERT INTO `reward` VALUES ('1', '1', 1);
INSERT INTO `reward` VALUES ('1', '2', 30);
INSERT INTO `reward` VALUES ('2', '999', 1);
INSERT INTO `reward` VALUES ('3', '999', 20);
-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `RoleID` int NOT NULL AUTO_INCREMENT,
  `RoleName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`RoleID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, 'users');
INSERT INTO `roles` VALUES (2, 'admin');

-- ----------------------------
-- Table structure for userroles
-- ----------------------------
DROP TABLE IF EXISTS `userroles`;
CREATE TABLE `userroles`  (
  `Username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `RoleID` int NOT NULL,
  PRIMARY KEY (`Username`, `RoleID`) USING BTREE,
  INDEX `RoleID`(`RoleID` ASC) USING BTREE,
  CONSTRAINT `userroles_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `users` (`Username`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `userroles_ibfk_2` FOREIGN KEY (`RoleID`) REFERENCES `roles` (`RoleID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `Username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Password` char(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `MailAddress` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `NickName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `UserStatus` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1',
  `Balance` decimal(12, 2) NOT NULL DEFAULT 0.00,
  `changeNickCoolTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`Username`) USING BTREE,
  UNIQUE INDEX `UK_Users_Mail`(`MailAddress` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for userwarehouse
-- ----------------------------
DROP TABLE IF EXISTS `userwarehouse`;
CREATE TABLE `userwarehouse`  (
  `UserName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ItemID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Quantity` int NOT NULL,
  PRIMARY KEY (`UserName`, `ItemID`) USING BTREE,
  INDEX `ItemID`(`ItemID` ASC) USING BTREE,
  CONSTRAINT `userwarehouse_ibfk_1` FOREIGN KEY (`UserName`) REFERENCES `users` (`Username`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `userwarehouse_ibfk_2` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for verifycode
-- ----------------------------
DROP TABLE IF EXISTS `verifycode`;
CREATE TABLE `verifycode`  (
  `MailAddress` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Code` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ExpiresAt` datetime NOT NULL,
  `MailStatus` int(10) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  PRIMARY KEY (`MailAddress`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
