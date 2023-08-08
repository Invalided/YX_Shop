/*
 Navicat Premium Data Transfer

 Source Server         : CentOS7PracticeMySQL8.0
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 192.168.43.20:3306
 Source Schema         : practice

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 28/06/2023 00:16:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `age` int NOT NULL,
  `version` int NULL DEFAULT 1 COMMENT '乐观锁',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` int NULL DEFAULT 0 COMMENT '逻辑删除：0：未删除 1：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '张三', 'zhangsan', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (2, '李四', 'lisi', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (3, '王五', 'wangwu', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (4, '赵六', 'zhaoliu', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (5, '周七', 'zhouqi', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (6, '钱八', 'qianba', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (7, '孙九', 'sunjiu', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (8, '朱十', 'zhushi', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (9, '刘一', 'liuyi', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (10, '陈二', 'chener', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (11, '张三1', 'zhangsan1', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (12, '李四1', 'lisi1', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (13, '王五1', 'wangwu1', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (14, '赵六1', 'zhaoliu1', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (15, '周七1', 'zhouqi1', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (16, '钱八1', 'qianba1', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (17, '孙九1', 'sunjiu1', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (18, '朱十1', 'zhushi1', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (19, '刘一1', 'liuyi1', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (20, '陈二1', 'chener1', 18, 1, '2023-06-21 13:44:55', '2023-06-22 08:55:20', 0);
INSERT INTO `user` VALUES (21, 'put', '123456', 20, 16, '2023-06-22 00:55:20', '2023-06-27 23:28:06', 1);
INSERT INTO `user` VALUES (22, 'post', '123456', 20, 1, '2023-06-27 23:39:13', '2023-06-27 23:39:13', 0);

SET FOREIGN_KEY_CHECKS = 1;
