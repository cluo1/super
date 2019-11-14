a/*
Navicat MySQL Data Transfer

Source Server         : super
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : super

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2019-11-14 16:05:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `super_properties`
-- ----------------------------
DROP TABLE IF EXISTS `super_properties`;
CREATE TABLE `super_properties` (
  `application` varchar(64) NOT NULL,
  `property_name` varchar(128) NOT NULL,
  `property_value` varchar(500) NOT NULL,
  `profile` varchar(32) NOT NULL,
  `label` varchar(32) NOT NULL,
  PRIMARY KEY (`application`,`profile`,`property_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of super_properties
-- ----------------------------
INSERT INTO `super_properties` VALUES (''demo'', ''server.port'', ''8081'', ''dev'', ''master'');
INSERT INTO `super_properties` VALUES (''feign'', ''feign.hystrix.enabled'', ''true'', ''dev'', ''master'');
INSERT INTO `super_properties` VALUES (''feign'', ''server.connection-timeout'', ''10000'', ''dev'', ''master'');
INSERT INTO `super_properties` VALUES (''feign'', ''server.port'', ''8081'', ''dev'', ''master'');
INSERT INTO `super_properties` VALUES (''gateway'', ''management.endpoints.web.cors.allowed-methods'', ''*'', ''dev'', ''master'');
INSERT INTO `super_properties` VALUES (''gateway'', ''management.endpoints.web.cors.allowed-origins'', ''*'', ''dev'', ''master'');
INSERT INTO `super_properties` VALUES (''gateway'', ''management.endpoints.web.exposure.include'', ''*'', ''dev'', ''master'');
INSERT INTO `super_properties` VALUES (''gateway'', ''server.port'', ''8020'', ''dev'', ''master'');
INSERT INTO `super_properties` VALUES (''gateway'', ''spring.cloud.gateway.discovery.locator.enabled'', ''true'', ''dev'', ''master'');
INSERT INTO `super_properties` VALUES (''gateway'', ''spring.cloud.gateway.discovery.locator.lowerCaseServiceId'', ''true'', ''dev'', ''master'');
