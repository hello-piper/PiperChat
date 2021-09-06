/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
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
package io.piper.server.spring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("ImUserDTO")
public class ImUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("conversationId")
    private String conversationId;

    @ApiModelProperty("nickname")
    private String nickname;

    @ApiModelProperty("avatar")
    private String avatar;

    @ApiModelProperty("phone")
    private String phone;

    @ApiModelProperty("email")
    private String email;

    @ApiModelProperty("age")
    private Integer age;

    @ApiModelProperty("gender")
    private Integer gender;

    @ApiModelProperty("status")
    private Integer status;

    @ApiModelProperty("createUid")
    private Long createUid;

    @ApiModelProperty("createTime")
    private Long createTime;
}
