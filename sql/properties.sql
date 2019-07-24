/*
Navicat MySQL Data Transfer

Source Server         : super
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : super

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2019-07-24 16:31:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `properties`
-- ----------------------------
DROP TABLE IF EXISTS `properties`;
CREATE TABLE `properties` (
  `application` varchar(50) NOT NULL,
  `property_name` varchar(50) NOT NULL,
  `property_value` varchar(500) NOT NULL,
  `profile` varchar(50) NOT NULL,
  `label` varchar(50) NOT NULL,
  PRIMARY KEY (`application`,`profile`,`property_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of properties
-- ----------------------------

INSERT INTO `properties` VALUES ('gateway-server', 'management.endpoints.web.cors.allowed-methods', '*', 'dev', 'master');
INSERT INTO `properties` VALUES ('gateway-server', 'management.endpoints.web.cors.allowed-origins', '*', 'dev', 'master');
INSERT INTO `properties` VALUES ('gateway-server', 'management.endpoints.web.exposure.include', '*', 'dev', 'master');
INSERT INTO `properties` VALUES ('gateway-server', 'server.port', '8020', 'dev', 'master');
INSERT INTO `properties` VALUES ('gateway-server', 'spring.cloud.bus.enabled', 'true', 'dev', 'master');
INSERT INTO `properties` VALUES ('gateway-server', 'spring.cloud.bus.trace.enableds', 'true', 'dev', 'master');
INSERT INTO `properties` VALUES ('gateway-server', 'spring.rabbitmq.host', 'localhost', 'dev', 'master');
INSERT INTO `properties` VALUES ('gateway-server', 'spring.rabbitmq.password', 'guest', 'dev', 'master');
INSERT INTO `properties` VALUES ('gateway-server', 'spring.rabbitmq.port', '5672', 'dev', 'master');
INSERT INTO `properties` VALUES ('gateway-server', 'spring.rabbitmq.username', 'guest', 'dev', 'master');
INSERT INTO `properties` VALUES ('gateway-server', 'zuul.ignored-services', '*', 'dev', 'master');
INSERT INTO `properties` VALUES ('gateway-server', 'zuul.routes.config-server', '/config/**', 'dev', 'master');
INSERT INTO `properties` VALUES ('gateway-server', 'zuul.routes.demo-server', '/demo-service/**', 'dev', 'master');
INSERT INTO `properties` VALUES ('gateway-server', 'zuul.routes.feign-server', '/feign-service/**', 'dev', 'master');

INSERT INTO `properties` VALUES ('demo-server', 'server.port', '8081', 'dev', 'master');

INSERT INTO `properties` VALUES ('feign-server', 'feign.hystrix.enabled', 'true', 'dev', 'master');
INSERT INTO `properties` VALUES ('feign-server', 'server.connection-timeout', '10000', 'dev', 'master');
INSERT INTO `properties` VALUES ('feign-server', 'server.port', '8082', 'dev', 'master');