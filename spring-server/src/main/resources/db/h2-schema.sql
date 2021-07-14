DROP TABLE IF EXISTS `im_message`;
CREATE TABLE `im_message`  (
  `id` bigint(20) NOT NULL,
  `chat_type` tinyint(2) NULL DEFAULT NULL,
  `msg_type` tinyint(2) NULL DEFAULT NULL,
  `from` varchar(20) NULL DEFAULT '',
  `to` varchar(20) NULL DEFAULT '',
  `body` varchar(1024) NULL DEFAULT '',
  `extra` varchar(1024) NULL DEFAULT '',
  `create_time` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `im_user`;
CREATE TABLE `im_user`  (
  `id` bigint(20) NOT NULL,
  `nickname` varchar(16) NULL DEFAULT '',
  `avatar` varchar(255) NULL DEFAULT '',
  `phone` varchar(16) NULL DEFAULT '',
  `email` varchar(32) NULL DEFAULT '',
  `age` int(3) NULL DEFAULT NULL,
  `gender` int(1) NULL DEFAULT NULL,
  `password` varchar(255) NULL DEFAULT '',
  `salt` varchar(255) NULL DEFAULT '',
  `status` int(1) NULL DEFAULT 0,
  `create_user` varchar(20) NULL DEFAULT '',
  `create_time` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);
