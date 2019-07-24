/*
Navicat MySQL Data Transfer

Source Server         : super
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : super

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2019-07-24 19:32:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `super_user`
-- ----------------------------
DROP TABLE IF EXISTS `super_user`;
CREATE TABLE `super_user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of super_user
-- ----------------------------
