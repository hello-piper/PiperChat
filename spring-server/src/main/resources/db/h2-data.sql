INSERT INTO `im_user` (`id`, `nickname`, `avatar`, `phone`, `email`, `age`, `gender`, `password`, `salt`, `status`, `create_uid`, `create_time`)
VALUES (0, 'Piper', 'https://profile.csdnimg.cn/6/C/F/1_gy325416', '18900000000', '0@piper.io', '0', '0', '86b9af2197534fce1dc9c5fa0d37e9e7', 'vs3nz', '0', 0, 1598976000000);

INSERT INTO `im_user` (`id`, `nickname`, `avatar`, `phone`, `email`, `age`, `gender`, `password`, `salt`, `status`, `create_uid`, `create_time`)
VALUES (1, 'Piper1', 'https://profile.csdnimg.cn/6/C/F/1_gy325416', '18900000000', '1@piper.io', '0', '0', '86b9af2197534fce1dc9c5fa0d37e9e7', 'vs3nz', '0', 0, 1598976000000);

INSERT INTO `im_message`(`id`, `chat_type`, `msg_type`, `from`, `to`, `conversation_id`, `title`, `server_time`)
VALUES (0, 0, 0, 0, 1, '0:1', 'Hello', 1598976000000);
