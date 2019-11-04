INSERT INTO `super_properties` VALUES ('gateway-server', 'zuul.routes.system-server', '/system/**', 'dev', 'master');
INSERT INTO `super_properties` VALUES ('system-server', 'server.port', '8082', 'dev', 'master');
INSERT INTO `super_properties` VALUES ('system-server', 'spring.datasource.druid.url', 'jdbc:mysql://localhost:3306/super?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8', 'dev', 'master');
INSERT INTO `super_properties` VALUES ('system-server', 'spring.datasource.username', 'root', 'dev', 'master');
INSERT INTO `super_properties` VALUES ('system-server', 'spring.datasource.password', 'root', 'dev', 'master');
INSERT INTO `super_properties` VALUES ('system-server', 'spring.datasource.driver-class-name', 'com.mysql.jdbc.Driver', 'dev', 'master');
INSERT INTO `super_properties` VALUES ('system-server', 'spring.datasource.type', 'com.alibaba.druid.pool.DruidDataSource', 'dev', 'master');
INSERT INTO `super_properties` VALUES ('system-server', 'mybatis.mapperLocations', 'classpath:mapper/*Mapper.xml', 'dev', 'master');