/*
 * Copyright 2020 The PiperChat
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.common.pojo.entity;

import java.io.Serializable;

/**
 * 群组信息
 *
 * @author piper
 */
public class ImGroup implements Serializable {
    private static final long serialVersionUID = 1;

    private String id;
    private String groupName;
    private String owner;
    private String announcement;
    private Integer type;
    private Integer status;
    private String createUser;
    private String createTime;
}
