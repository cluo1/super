/*
Navicat MySQL Data Transfer

Source Server         : super
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : super

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2019-08-01 16:39:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `super_menu`
-- ----------------------------
DROP TABLE IF EXISTS `super_menu`;
CREATE TABLE `super_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(64) DEFAULT NULL,
  `path` varchar(64) DEFAULT NULL,
  `component` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `iconCls` varchar(64) DEFAULT NULL,
  `keepAlive` tinyint(1) DEFAULT NULL,
  `requireAuth` tinyint(1) DEFAULT NULL,
  `parent_Id` int(11) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `parent_Id` (`parent_Id`),
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`parent_Id`) REFERENCES `super_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of super_menu
-- ----------------------------
INSERT INTO `super_menu` VALUES ('1', '/', null, null, '所有', null, null, null, null, '0');
INSERT INTO `super_menu` VALUES ('2', '/', '/home', 'Home', '员工资料', 'fa fa-user-circle-o', null, '1', '1', '0');
INSERT INTO `super_menu` VALUES ('3', '/', '/home', 'Home', '人事管理', 'fa fa-address-card-o', null, '1', '1', '0');
INSERT INTO `super_menu` VALUES ('4', '/', '/home', 'Home', '薪资管理', 'fa fa-money', null, '1', '1', '0');
INSERT INTO `super_menu` VALUES ('5', '/', '/home', 'Home', '统计管理', 'fa fa-bar-chart', null, '1', '1', '0');
INSERT INTO `super_menu` VALUES ('6', '/', '/home', 'Home', '系统管理', 'fa fa-windows', null, '1', '1', '1');
INSERT INTO `super_menu` VALUES ('7', '/employee/basic/**', '/emp/basic', 'EmpBasic', '基本资料', null, null, '1', '2', '0');
INSERT INTO `super_menu` VALUES ('8', '/employee/advanced/**', '/emp/adv', 'EmpAdv', '高级资料', null, null, '1', '2', '0');
INSERT INTO `super_menu` VALUES ('9', '/personnel/emp/**', '/per/emp', 'PerEmp', '员工资料', null, null, '1', '3', '0');
INSERT INTO `super_menu` VALUES ('10', '/personnel/ec/**', '/per/ec', 'PerEc', '员工奖惩', null, null, '1', '3', '0');
INSERT INTO `super_menu` VALUES ('11', '/personnel/train/**', '/per/train', 'PerTrain', '员工培训', null, null, '1', '3', '0');
INSERT INTO `super_menu` VALUES ('12', '/personnel/salary/**', '/per/salary', 'PerSalary', '员工调薪', null, null, '1', '3', '0');
INSERT INTO `super_menu` VALUES ('13', '/personnel/remove/**', '/per/mv', 'PerMv', '员工调动', null, null, '1', '3', '0');
INSERT INTO `super_menu` VALUES ('14', '/salary/sob/**', '/sal/sob', 'SalSob', '工资账套管理', null, null, '1', '4', '0');
INSERT INTO `super_menu` VALUES ('15', '/salary/sobcfg/**', '/sal/sobcfg', 'SalSobCfg', '员工账套设置', null, null, '1', '4', '0');
INSERT INTO `super_menu` VALUES ('16', '/salary/table/**', '/sal/table', 'SalTable', '工资表管理', null, null, '1', '4', '0');
INSERT INTO `super_menu` VALUES ('17', '/salary/month/**', '/sal/month', 'SalMonth', '月末处理', null, null, '1', '4', '0');
INSERT INTO `super_menu` VALUES ('18', '/salary/search/**', '/sal/search', 'SalSearch', '工资表查询', null, null, '1', '4', '0');
INSERT INTO `super_menu` VALUES ('19', '/statistics/all/**', '/sta/all', 'StaAll', '综合信息统计', null, null, '1', '5', '0');
INSERT INTO `super_menu` VALUES ('20', '/statistics/score/**', '/sta/score', 'StaScore', '员工积分统计', null, null, '1', '5', '0');
INSERT INTO `super_menu` VALUES ('21', '/statistics/personnel/**', '/sta/pers', 'StaPers', '人事信息统计', null, null, '1', '5', '0');
INSERT INTO `super_menu` VALUES ('22', '/statistics/recored/**', '/sta/record', 'StaRecord', '人事记录统计', null, null, '1', '5', '0');
INSERT INTO `super_menu` VALUES ('23', '/system/basic/**', '/sys/basic', 'SysBasic', '基础信息设置', null, null, '1', '6', '1');
INSERT INTO `super_menu` VALUES ('24', '/system/cfg/**', '/sys/cfg', 'SysCfg', '系统管理', null, null, '1', '6', '1');
INSERT INTO `super_menu` VALUES ('25', '/system/log/**', '/sys/log', 'SysLog', '操作日志管理', null, null, '1', '6', '1');
INSERT INTO `super_menu` VALUES ('26', '/system/hr/**', '/sys/hr', 'SysHr', '操作员管理', null, null, '1', '6', '1');
INSERT INTO `super_menu` VALUES ('27', '/system/data/**', '/sys/data', 'SysData', '备份恢复数据库', null, null, '1', '6', '1');
INSERT INTO `super_menu` VALUES ('28', '/system/init/**', '/sys/init', 'SysInit', '初始化数据库', null, null, '1', '6', '1');

-- ----------------------------
-- Table structure for `super_menu_role`
-- ----------------------------
DROP TABLE IF EXISTS `super_menu_role`;
CREATE TABLE `super_menu_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `menu_id` (`menu_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `sm_id` FOREIGN KEY (`menu_id`) REFERENCES `super_menu` (`id`),
  CONSTRAINT `sr_id` FOREIGN KEY (`role_id`) REFERENCES `super_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of super_menu_role
-- ----------------------------
INSERT INTO `super_menu_role` VALUES ('1', '6', '1');
INSERT INTO `super_menu_role` VALUES ('2', '23', '1');
INSERT INTO `super_menu_role` VALUES ('3', '24', '1');
INSERT INTO `super_menu_role` VALUES ('4', '25', '1');
INSERT INTO `super_menu_role` VALUES ('5', '26', '1');
INSERT INTO `super_menu_role` VALUES ('6', '27', '1');
INSERT INTO `super_menu_role` VALUES ('7', '28', '1');

-- ----------------------------
-- Table structure for `super_properties`
-- ----------------------------
DROP TABLE IF EXISTS `super_properties`;
CREATE TABLE `super_properties` (
  `application` varchar(50) NOT NULL,
  `property_name` varchar(50) NOT NULL,
  `property_value` varchar(500) NOT NULL,
  `profile` varchar(50) NOT NULL,
  `label` varchar(50) NOT NULL,
  PRIMARY KEY (`application`,`profile`,`property_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `super_role`
-- ----------------------------
DROP TABLE IF EXISTS `super_role`;
CREATE TABLE `super_role` (
  `role_id` int(10) NOT NULL AUTO_INCREMENT,
  `role_name_en` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `role_name_zh` varchar(64) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of super_role
-- ----------------------------
INSERT INTO `super_role` VALUES ('1', 'ROLE_admin', '系统管理员');
INSERT INTO `super_role` VALUES ('2', 'ROLE_test2', '测试角色2');
INSERT INTO `super_role` VALUES ('3', 'ROLE_test1', '测试角色1');

-- ----------------------------
-- Table structure for `super_user`
-- ----------------------------
DROP TABLE IF EXISTS `super_user`;
CREATE TABLE `super_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` char(11) DEFAULT NULL COMMENT '手机号码',
  `address` varchar(64) DEFAULT NULL COMMENT '联系地址',
  `enabled` tinyint(1) DEFAULT '1',
  `userface` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of super_user
-- ----------------------------
INSERT INTO `super_user` VALUES ('1', 'super', '$2a$10$ySG2lkvjFHY5O0./CPIE1OI8VJsuKYEzOYzqIa7AJR6sEgSzUFOAm', '18656692693', '安徽', '1', 'http://bpic.588ku.com/element_pic/01/40/00/64573ce2edc0728.jpg', null);
INSERT INTO `super_user` VALUES ('2', 'super2', '$2a$10$ySG2lkvjFHY5O0./CPIE1OI8VJsuKYEzOYzqIa7AJR6sEgSzUFOAm', '18656692693', '安徽', '1', 'http://bpic.588ku.com/element_pic/01/40/00/64573ce2edc0728.jpg', null);
INSERT INTO `super_user` VALUES ('3', 'super3', '$2a$10$ySG2lkvjFHY5O0./CPIE1OI8VJsuKYEzOYzqIa7AJR6sEgSzUFOAm', '18656692693', '安徽', '1', 'http://bpic.588ku.com/element_pic/01/40/00/64573ce2edc0728.jpg', null);

-- ----------------------------
-- Table structure for `super_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `super_user_role`;
CREATE TABLE `super_user_role` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_Id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `u_idx` (`user_Id`),
  KEY `r_idx` (`role_id`),
  CONSTRAINT `sr_rid` FOREIGN KEY (`role_id`) REFERENCES `super_role` (`role_id`),
  CONSTRAINT `su_uid` FOREIGN KEY (`user_Id`) REFERENCES `super_user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of super_user_role
-- ----------------------------
INSERT INTO `super_user_role` VALUES ('1', '1', '1');
INSERT INTO `super_user_role` VALUES ('2', '2', '2');
INSERT INTO `super_user_role` VALUES ('3', '3', '3');
