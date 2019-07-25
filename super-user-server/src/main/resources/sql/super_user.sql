/*
Navicat MySQL Data Transfer

Source Server         : super
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : super

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2019-07-24 20:18:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `super_user`
-- ----------------------------
DROP TABLE IF EXISTS `super_user`;
CREATE TABLE `super_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of super_user
-- ----------------------------
INSERT INTO `super_user` VALUES ('1', 'super', 'super', '18656692693');
INSERT INTO `super_user` VALUES ('2', 'super2', 'super2', '18656692693');
INSERT INTO `super_user` VALUES ('3', 'super3', 'super3', '18656692693');
