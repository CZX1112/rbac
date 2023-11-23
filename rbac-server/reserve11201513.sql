/*
 Navicat Premium Data Transfer

 Source Server         : MYSQL
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : rbac

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 08/11/2023 20:44:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


CREATE DATABASE IF NOT EXISTS `rbac` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `rbac`;


-- ----------------------------
-- Table structure for menu_permissions
-- ----------------------------
DROP TABLE IF EXISTS `menu_permissions`;
CREATE TABLE `menu_permissions`  (
                                     `menu_id` int(0) NOT NULL,
                                     `permission_id` int(0) NOT NULL,
                                     PRIMARY KEY (`menu_id`, `permission_id`) USING BTREE,
                                     INDEX `pp`(`permission_id`) USING BTREE,
                                     CONSTRAINT `mm` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`menu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                     CONSTRAINT `pp` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`permission_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_permissions
-- ----------------------------
INSERT INTO `menu_permissions` VALUES (1, 1);
INSERT INTO `menu_permissions` VALUES (2, 2);
INSERT INTO `menu_permissions` VALUES (3, 3);
INSERT INTO `menu_permissions` VALUES (4, 4);
INSERT INTO `menu_permissions` VALUES (5, 5);
INSERT INTO `menu_permissions` VALUES (6, 6);
INSERT INTO `menu_permissions` VALUES (7, 7);

INSERT INTO `menu_permissions` VALUES (8, 16);
INSERT INTO `menu_permissions` VALUES (9, 19);

-- ----------------------------
-- Table structure for menus
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus`  (
                          `menu_id` int(0) NOT NULL AUTO_INCREMENT,
                          `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                          `parent_id` int(0) NULL DEFAULT NULL,
                          `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                          `order` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                          PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menus
-- ----------------------------
INSERT INTO `menus` VALUES (1, '用户列表', 0, '/rbac/user/list', '1');
INSERT INTO `menus` VALUES (2, '新增用户', 0, '/rbac/user/add', '2');
INSERT INTO `menus` VALUES (3, '用户管理', 0, '/rbac/user/manage', '3');
INSERT INTO `menus` VALUES (4, '角色列表', 0, '/rbac/role/list', '4');
INSERT INTO `menus` VALUES (5, '新增角色', 0, '/rbac/role/add', '5');
INSERT INTO `menus` VALUES (6, '角色管理', 0, '/rbac/role/manage', '6');
INSERT INTO `menus` VALUES (7, '权限列表', 0, '/rbac/permission/list', '7');
INSERT INTO `menus` VALUES (8, '新增权限', 0, '/rbac/permission/add', '8');
INSERT INTO `menus` VALUES (9, '权限管理', 0, '/rbac/permission/manage', '9');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
                                `permission_id` int(0) NOT NULL AUTO_INCREMENT,
                                `permission_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO `permissions` VALUES (1, '查看用户列表', '可以查看用户列表', '/user/list');
INSERT INTO `permissions` VALUES (2, '新增用户', '可以新增用户', '/user/add');
INSERT INTO `permissions` VALUES (3, '权限管理', '可以管理用户的权限', '/user/manage');
INSERT INTO `permissions` VALUES (4, '查看角色列表', '可以查看角色列表', '/role/list');
INSERT INTO `permissions` VALUES (5, '新增角色', '可以新增角色', '/role/add');
INSERT INTO `permissions` VALUES (6, '角色管理', '可以管理角色的权限等信息', '/role/manage');
INSERT INTO `permissions` VALUES (7, '查看权限列表', '可以查看所有权限信息', '/permission/list');

INSERT INTO `permissions` VALUES (8, '删除用户', '可以删除指定用户', '/user/delete');
INSERT INTO `permissions` VALUES (9, '更新用户', '可以更新指定用户信息', '/user/update');

INSERT INTO `permissions` VALUES (10, '删除角色', '可以删除指定角色', '/role/delete');
INSERT INTO `permissions` VALUES (11, '更新角色', '编辑角色信息', '/role/update');

INSERT INTO `permissions` VALUES (12, '初始化角色', '可以为指定用户初始化角色', '/userrole/add');
INSERT INTO `permissions` VALUES (13, '设置角色', '可以为指定用户设置角色', '/userrole/update');

INSERT INTO `permissions` VALUES (14, '为角色增加权限', '可以为指定角色增加相关权限', '/permission/add');
INSERT INTO `permissions` VALUES (15, '删除角色权限', '可以删除指定角色的所有权限', '/permission/delete');

INSERT INTO `permissions` VALUES (16, '增加权限', '可以增加相关权限', '/permission/addPermission');
INSERT INTO `permissions` VALUES (17, '删除权限', '可以根据编号删除相关权限', '/permission/deleteByPermissionId');
INSERT INTO `permissions` VALUES (18, '更新权限', '可以更新相关权限信息', '/permission/update');
INSERT INTO `permissions` VALUES (19, '权限管理', '可以执行相关权限管理', '/permission/manage');


-- ----------------------------
-- Table structure for role_permissions
-- ----------------------------
DROP TABLE IF EXISTS `role_permissions`;
CREATE TABLE `role_permissions`  (
                                     `role_id` int(0) NOT NULL,
                                     `permission_id` int(0) NOT NULL,
                                     PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
                                     INDEX `permission_id1`(`permission_id`) USING BTREE
-- #                                      CONSTRAINT `permission_id1` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`permission_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
-- #                                      CONSTRAINT `role_id1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permissions
-- ----------------------------
INSERT INTO `role_permissions` VALUES (3, 1);
INSERT INTO `role_permissions` VALUES (3, 2);
INSERT INTO `role_permissions` VALUES (3, 3);
INSERT INTO `role_permissions` VALUES (3, 4);
INSERT INTO `role_permissions` VALUES (3, 5);
INSERT INTO `role_permissions` VALUES (3, 6);
INSERT INTO `role_permissions` VALUES (3, 7);
INSERT INTO `role_permissions` VALUES (3, 8);
INSERT INTO `role_permissions` VALUES (3, 9);
INSERT INTO `role_permissions` VALUES (3, 10);
INSERT INTO `role_permissions` VALUES (3, 11);
INSERT INTO `role_permissions` VALUES (3, 12);
INSERT INTO `role_permissions` VALUES (3, 13);
INSERT INTO `role_permissions` VALUES (3, 14);
INSERT INTO `role_permissions` VALUES (3, 15);
INSERT INTO `role_permissions` VALUES (3, 16);
INSERT INTO `role_permissions` VALUES (3, 17);
INSERT INTO `role_permissions` VALUES (3, 18);
INSERT INTO `role_permissions` VALUES (3, 19);

INSERT INTO `role_permissions` VALUES (2, 1);
INSERT INTO `role_permissions` VALUES (2, 2);
INSERT INTO `role_permissions` VALUES (2, 4);
INSERT INTO `role_permissions` VALUES (2, 5);
INSERT INTO `role_permissions` VALUES (2, 7);
INSERT INTO `role_permissions` VALUES (2, 14);
INSERT INTO `role_permissions` VALUES (2, 16);

INSERT INTO `role_permissions` VALUES (1, 1);
INSERT INTO `role_permissions` VALUES (1, 4);
INSERT INTO `role_permissions` VALUES (1, 7);

# INSERT INTO `role_permissions` VALUES (2, 1);
# INSERT INTO `role_permissions` VALUES (3, 1);
# INSERT INTO `role_permissions` VALUES (2, 2);
# INSERT INTO `role_permissions` VALUES (3, 2);
# INSERT INTO `role_permissions` VALUES (3, 3);
# INSERT INTO `role_permissions` VALUES (1, 4);
# INSERT INTO `role_permissions` VALUES (2, 4);
# INSERT INTO `role_permissions` VALUES (3, 4);
# INSERT INTO `role_permissions` VALUES (2, 5);
# INSERT INTO `role_permissions` VALUES (3, 5);
# INSERT INTO `role_permissions` VALUES (3, 6);
# INSERT INTO `role_permissions` VALUES (1, 7);
# INSERT INTO `role_permissions` VALUES (2, 7);
# INSERT INTO `role_permissions` VALUES (3, 7);

-- ----------------------------
-- Table structure for roles
-- ----------------------------
-- DROP TABLE IF EXISTS `roles`;
-- CREATE TABLE `roles`  (
--                           `role_id` int(0) NOT NULL AUTO_INCREMENT,
--                           `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
--                           `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
--                           PRIMARY KEY (`role_id`) USING BTREE
-- ) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- -- ----------------------------
-- -- Records of roles
-- -- ----------------------------
-- INSERT INTO `roles` VALUES (1, '普通用户', '仅可使用普通用户功能，不具有管理权限');
-- INSERT INTO `roles` VALUES (2, '管理员', '拥有管理权限');
-- INSERT INTO `roles` VALUES (3, '核心管理员', '拥有所有权限');


CREATE TABLE role (
                      role_id INT AUTO_INCREMENT PRIMARY KEY,
                      rolename VARCHAR(255) NOT NULL,
                      description VARCHAR(255),
                      createTime DATETIME,
                      updateTime DATETIME
);

INSERT INTO role (rolename, description, createTime, updateTime)
VALUES
    ('普通用户', '没啥权限，只有做普通用户做的。', NOW(), NOW()),
    ('管理员', '普通管理员，有一些权限但不多。', NOW(), NOW()),
    ('超级管理员', '超级牛的超级管理员，拥有至高无上的权限。', NOW(), NOW());

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `user_id` int(0) NOT NULL AUTO_INCREMENT,
                         `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `status` tinyint(1) NULL DEFAULT NULL,
                         `createTime` DATETIME,
                         `updateTime` DATETIME,
                         PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '管理员', '123456', '18927212345', 'ad@example.com', '上海市普陀区', '男', 1, NOW(), NOW());
INSERT INTO `user` VALUES (2, 'user1', '用户1', '123456', '189222', 'xm@example.com', '北京市海淀区', '男', 1, NOW(), NOW());
INSERT INTO `user` VALUES (3, 'superadmin', '超级管理员', '123456', '1892', 'boss@example.com', '福建省厦门市思明区', '男', 1, NOW(), NOW());

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`  (
                               `user_id` int(0) NOT NULL,
                               `role_id` int(0) NOT NULL,
                               PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
                               INDEX `role_id`(`role_id`) USING BTREE
-- #                                CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
-- #                                CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES (2, 1);
INSERT INTO `user_roles` VALUES (1, 2);
INSERT INTO `user_roles` VALUES (3, 3);

SET FOREIGN_KEY_CHECKS = 1;
