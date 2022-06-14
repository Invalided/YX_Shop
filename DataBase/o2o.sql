/*
 Navicat Premium Data Transfer

 Source Server         : test_mysql
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : o2o

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 28/06/2021 15:55:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_area
-- ----------------------------
DROP TABLE IF EXISTS `tb_area`;
CREATE TABLE `tb_area`  (
                            `area_id` int(2) NOT NULL AUTO_INCREMENT,
                            `area_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `priority` int(2) NOT NULL DEFAULT 0,
                            `create_time` datetime(0) NULL DEFAULT NULL,
                            `last_edit_time` datetime(0) NULL DEFAULT NULL,
                            PRIMARY KEY (`area_id`) USING BTREE,
                            UNIQUE INDEX `uk_area`(`area_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_award
-- ----------------------------
DROP TABLE IF EXISTS `tb_award`;
CREATE TABLE `tb_award`  (
                             `award_id` int(10) NOT NULL AUTO_INCREMENT,
                             `award_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
                             `award_desc` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
                             `award_img` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
                             `point` int(10) NOT NULL DEFAULT 0,
                             `priority` int(2) NULL DEFAULT NULL,
                             `create_time` datetime(0) NULL DEFAULT NULL,
                             `last_edit_time` datetime(0) NULL DEFAULT NULL,
                             `enable_status` int(2) NOT NULL DEFAULT 0,
                             `shop_id` int(10) NULL DEFAULT NULL,
                             PRIMARY KEY (`award_id`) USING BTREE,
                             INDEX `fk_award_shop_idx`(`shop_id`) USING BTREE,
                             CONSTRAINT `fk_award_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_head_line
-- ----------------------------
DROP TABLE IF EXISTS `tb_head_line`;
CREATE TABLE `tb_head_line`  (
                                 `line_id` int(100) NOT NULL AUTO_INCREMENT,
                                 `line_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `line_link` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `line_img` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `priority` int(2) NULL DEFAULT NULL,
                                 `enable_status` int(2) NOT NULL DEFAULT 0,
                                 `create_time` datetime(0) NULL DEFAULT NULL,
                                 `last_edit_time` datetime(0) NULL DEFAULT NULL,
                                 PRIMARY KEY (`line_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_local_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_local_auth`;
CREATE TABLE `tb_local_auth`  (
                                  `local_auth_id` int(10) NOT NULL AUTO_INCREMENT,
                                  `user_id` int(10) NOT NULL,
                                  `username` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                  `create_time` datetime(0) NULL DEFAULT NULL,
                                  `last_edit_time` datetime(0) NULL DEFAULT NULL,
                                  PRIMARY KEY (`local_auth_id`) USING BTREE,
                                  UNIQUE INDEX `uk_local_profile`(`username`) USING BTREE,
                                  INDEX `fk_localauth_profile`(`user_id`) USING BTREE,
                                  CONSTRAINT `fk_localauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_person_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_person_info`;
CREATE TABLE `tb_person_info`  (
                                   `user_id` int(10) NOT NULL AUTO_INCREMENT,
                                   `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `profile_img` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `email` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `gender` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `enable_status` int(2) NOT NULL DEFAULT 0 COMMENT '0:账号封禁,1:启用',
                                   `user_type` int(2) NOT NULL DEFAULT 1 COMMENT '1.顾客，2.店家，3.超级管理员',
                                   `create_time` datetime(0) NULL DEFAULT NULL,
                                   `last_edit_time` datetime(0) NULL DEFAULT NULL,
                                   PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_product
-- ----------------------------
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product`  (
                               `product_id` int(100) NOT NULL AUTO_INCREMENT,
                               `product_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `product_desc` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `img_addr` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
                               `normal_price` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `promotion_price` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `priority` int(2) NOT NULL DEFAULT 0,
                               `create_time` datetime(0) NULL DEFAULT NULL,
                               `last_edit_time` datetime(0) NULL DEFAULT NULL,
                               `enable_status` int(2) NOT NULL DEFAULT 0,
                               `product_category_id` int(11) NULL DEFAULT NULL,
                               `shop_id` int(20) NOT NULL DEFAULT 0,
                               `point` int(10) NOT NULL DEFAULT 0,
                               PRIMARY KEY (`product_id`) USING BTREE,
                               INDEX `fk_product_procate`(`product_category_id`) USING BTREE,
                               INDEX `fk_product_shop`(`shop_id`) USING BTREE,
                               CONSTRAINT `fk_product_procate` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`product_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                               CONSTRAINT `fk_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_product_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_category`;
CREATE TABLE `tb_product_category`  (
                                        `product_category_id` int(11) NOT NULL AUTO_INCREMENT,
                                        `product_category_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `priority` int(2) NULL DEFAULT 0,
                                        `create_time` datetime(0) NULL DEFAULT NULL,
                                        `shop_id` int(20) NOT NULL DEFAULT 0,
                                        PRIMARY KEY (`product_category_id`) USING BTREE,
                                        INDEX `fk_procate_shop`(`shop_id`) USING BTREE,
                                        CONSTRAINT `fk_procate_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_product_img
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_img`;
CREATE TABLE `tb_product_img`  (
                                   `product_img_id` int(20) NOT NULL AUTO_INCREMENT,
                                   `img_addr` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `img_desc` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `priority` int(2) NULL DEFAULT 0,
                                   `create_time` datetime(0) NULL DEFAULT NULL,
                                   `product_id` int(20) NULL DEFAULT NULL,
                                   PRIMARY KEY (`product_img_id`) USING BTREE,
                                   INDEX `fk_proimg_product`(`product_id`) USING BTREE,
                                   CONSTRAINT `fk_proimg_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_product_sell_daily
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_sell_daily`;
CREATE TABLE `tb_product_sell_daily`  (
                                          `product_id` int(100) NULL DEFAULT NULL,
                                          `shop_id` int(10) NULL DEFAULT NULL,
                                          `create_time` datetime(0) NULL DEFAULT NULL,
                                          `total` int(10) NULL DEFAULT 0,
                                          `product_sell_daily_id` int(100) NOT NULL AUTO_INCREMENT,
                                          PRIMARY KEY (`product_sell_daily_id`) USING BTREE,
                                          INDEX `fk_product_sell_product`(`product_id`) USING BTREE,
                                          INDEX `fk_product_sell_shop`(`shop_id`) USING BTREE,
                                          CONSTRAINT `fk_product_sell_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                          CONSTRAINT `fk_product_sell_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 845 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_shop
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop`  (
                            `shop_id` int(10) NOT NULL AUTO_INCREMENT,
                            `owner_id` int(10) NOT NULL COMMENT '店铺创建人',
                            `area_id` int(5) NULL DEFAULT NULL,
                            `shop_category_id` int(11) NULL DEFAULT NULL,
                            `shop_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `shop_desc` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `shop_addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `phone` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `shop_img` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `priority` int(3) NULL DEFAULT 0,
                            `create_time` datetime(0) NULL DEFAULT NULL,
                            `last_edit_time` datetime(0) NULL DEFAULT NULL,
                            `enable_status` int(2) NOT NULL DEFAULT 0,
                            `advice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            PRIMARY KEY (`shop_id`) USING BTREE,
                            INDEX `fk_shop_area`(`area_id`) USING BTREE,
                            INDEX `fk_shop_profile`(`owner_id`) USING BTREE,
                            INDEX `fk_shop_shopcate`(`shop_category_id`) USING BTREE,
                            CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `fk_shop_shopcate` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_shop_auth_map
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_auth_map`;
CREATE TABLE `tb_shop_auth_map`  (
                                     `shop_auth_id` int(10) NOT NULL AUTO_INCREMENT,
                                     `employee_id` int(10) NOT NULL,
                                     `shop_id` int(10) NOT NULL,
                                     `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
                                     `title_flag` int(2) NULL DEFAULT NULL,
                                     `create_time` datetime(0) NULL DEFAULT NULL,
                                     `last_edit_time` datetime(0) NULL DEFAULT NULL,
                                     `enable_status` int(2) NOT NULL DEFAULT 0,
                                     PRIMARY KEY (`shop_auth_id`) USING BTREE,
                                     INDEX `fk_shop_auth_map_shop`(`shop_id`) USING BTREE,
                                     INDEX `uk_shop_auth_map`(`employee_id`, `shop_id`) USING BTREE,
                                     CONSTRAINT `fk_shop_auth_map_employee` FOREIGN KEY (`employee_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                     CONSTRAINT `fk_shop_auth_map_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_shop_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_category`;
CREATE TABLE `tb_shop_category`  (
                                     `shop_category_id` int(11) NOT NULL AUTO_INCREMENT,
                                     `shop_category_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
                                     `shop_category_desc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
                                     `shop_category_img` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `priority` int(2) NOT NULL DEFAULT 0,
                                     `create_time` datetime(0) NULL DEFAULT NULL,
                                     `last_edit_time` datetime(0) NULL DEFAULT NULL,
                                     `parent_id` int(11) NULL DEFAULT NULL,
                                     PRIMARY KEY (`shop_category_id`) USING BTREE,
                                     INDEX `fk_shop_category_self`(`parent_id`) USING BTREE,
                                     CONSTRAINT `fk_shop_category_self` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user_award_map
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_award_map`;
CREATE TABLE `tb_user_award_map`  (
                                      `user_award_id` int(10) NOT NULL AUTO_INCREMENT,
                                      `user_id` int(10) NOT NULL,
                                      `award_id` int(10) NOT NULL,
                                      `shop_id` int(10) NOT NULL,
                                      `operator_id` int(10) NULL DEFAULT NULL,
                                      `create_time` datetime(0) NULL DEFAULT NULL,
                                      `used_status` int(2) NOT NULL DEFAULT 0,
                                      `point` int(10) NULL DEFAULT NULL,
                                      PRIMARY KEY (`user_award_id`) USING BTREE,
                                      INDEX `fk_user_award_map_profile`(`user_id`) USING BTREE,
                                      INDEX `fk_user_award_map_award`(`award_id`) USING BTREE,
                                      INDEX `fk_user_award_map_shop`(`shop_id`) USING BTREE,
                                      INDEX `fk_user_award_map_operator`(`operator_id`) USING BTREE,
                                      CONSTRAINT `fk_user_award_map_award` FOREIGN KEY (`award_id`) REFERENCES `tb_award` (`award_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                      CONSTRAINT `fk_user_award_map_operator` FOREIGN KEY (`operator_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                      CONSTRAINT `fk_user_award_map_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                      CONSTRAINT `fk_user_award_map_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user_product_map
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_product_map`;
CREATE TABLE `tb_user_product_map`  (
                                        `user_product_id` int(10) NOT NULL AUTO_INCREMENT,
                                        `user_id` int(10) NULL DEFAULT NULL,
                                        `product_id` int(100) NULL DEFAULT NULL,
                                        `shop_id` int(10) NULL DEFAULT NULL,
                                        `operator_id` int(10) NULL DEFAULT 1,
                                        `create_time` datetime(0) NULL DEFAULT NULL,
                                        `point` int(10) NULL DEFAULT 0,
                                        `nums` int(10) NULL DEFAULT NULL,
                                        PRIMARY KEY (`user_product_id`) USING BTREE,
                                        INDEX `fk_user_product_map_profile`(`user_id`) USING BTREE,
                                        INDEX `fk_user_product_map_product`(`product_id`) USING BTREE,
                                        INDEX `fk_user_product_map_shop`(`shop_id`) USING BTREE,
                                        INDEX `fk_user_product_map_operator`(`operator_id`) USING BTREE,
                                        CONSTRAINT `fk_user_product_map_operator` FOREIGN KEY (`operator_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                        CONSTRAINT `fk_user_product_map_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                        CONSTRAINT `fk_user_product_map_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                        CONSTRAINT `fk_user_product_map_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user_shop_map
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_shop_map`;
CREATE TABLE `tb_user_shop_map`  (
                                     `user_shop_id` int(10) NOT NULL AUTO_INCREMENT,
                                     `user_id` int(10) NOT NULL,
                                     `shop_id` int(10) NOT NULL,
                                     `create_time` datetime(0) NULL DEFAULT NULL,
                                     `point` int(10) NULL DEFAULT NULL,
                                     PRIMARY KEY (`user_shop_id`) USING BTREE,
                                     UNIQUE INDEX `uq_user_shop`(`user_id`, `shop_id`) USING BTREE,
                                     INDEX `fk_user_shop_shop`(`shop_id`) USING BTREE,
                                     CONSTRAINT `fk_user_shop_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                     CONSTRAINT `fk_user_shop_user` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_wechat_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_wechat_auth`;
CREATE TABLE `tb_wechat_auth`  (
                                   `wechat_auth_id` int(10) NOT NULL AUTO_INCREMENT,
                                   `user_id` int(10) NOT NULL,
                                   `create_time` datetime(0) NULL DEFAULT NULL,
                                   `open_id` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   PRIMARY KEY (`wechat_auth_id`) USING BTREE,
                                   UNIQUE INDEX `open_id`(`open_id`) USING BTREE,
                                   INDEX `fk_wechatauth_profile`(`user_id`) USING BTREE,
                                   CONSTRAINT `fk_wechatauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
