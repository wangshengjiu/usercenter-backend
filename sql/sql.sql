CREATE TABLE `user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
    `username` varchar(255) NOT NULL COMMENT '用户名',
    `user_account` varchar(255) DEFAULT NULL COMMENT '用户账号',
    `user_password` varchar(255) NOT NULL COMMENT '用户密码',
    `phone` varchar(11) DEFAULT NULL COMMENT '用户手机号',
    `gender` tinyint(2) DEFAULT NULL COMMENT '性别 1-男 0-女',
    `email` varchar(255) DEFAULT NULL COMMENT '用户邮箱',
    `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像',
    `user_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '用户状态 1-正常 0-禁用',
    `is_delete` tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否删除 1-正常 0-删除',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;