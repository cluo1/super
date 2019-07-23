/*
Navicat MySQL Data Transfer

Source Server         : super
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : super

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2019-07-23 18:03:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `properties`
-- ----------------------------
DROP TABLE IF EXISTS `properties`;
CREATE TABLE `properties` (
  `id` int(11) NOT NULL,
  `property_name` varchar(50) NOT NULL,
  `property_value` varchar(500) NOT NULL,
  `application` varchar(50) NOT NULL,
  `profile` varchar(50) NOT NULL,
  `label` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of properties
-- ----------------------------
INSERT INTO `properties` VALUES ('1', 'server.port', '8080', 'demo-server', 'dev', 'master');
INSERT INTO `properties` VALUES ('2', 'server.port', '8081', 'demo-server', 'test', 'master');
INSERT INTO `properties` VALUES ('3', 'spring.datasource.druid.url', 'jdbc:mysql://localhost:3306/super?useUnicode=true&characterEncoding=utf8', 'super-user-server', 'dev', 'master');
INSERT INTO `properties` VALUES ('4', 'spring.datasource.druid.username', 'root', 'super-user-server', 'dev', 'master');
INSERT INTO `properties` VALUES ('5', 'spring.datasource.druid.password', 'root', 'super-user-server', 'dev', 'master');
INSERT INTO `properties` VALUES ('6', 'mybatis.mapper-locations', 'classpath*:mybatis/*Mapper.xml', 'super-user-server', 'dev', 'master');
