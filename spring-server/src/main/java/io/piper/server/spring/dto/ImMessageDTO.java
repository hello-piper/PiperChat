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
@ApiModel("ImMessageDTO")
public class ImMessageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("chatType")
    private Byte chatType;

    @ApiModelProperty("msgType")
    private Byte msgType;

    @ApiModelProperty("from")
    private Long from;

    @ApiModelProperty("to")
    private Long to;

    @ApiModelProperty("conversationId")
    private String conversationId;

    @ApiModelProperty("sendTime")
    private Long sendTime;

    @ApiModelProperty("serverTime")
    private Long serverTime;

    @ApiModelProperty("title")
    private String title;

    @ApiModelProperty("imageMsgBody")
    private String imageMsgBody;

    @ApiModelProperty("voiceMsgBody")
    private String voiceMsgBody;

    @ApiModelProperty("videoMsgBody")
    private String videoMsgBody;

    @ApiModelProperty("fileMsgBody")
    private String fileMsgBody;

    @ApiModelProperty("locationMsgBody")
    private String locationMsgBody;

    @ApiModelProperty("cmdMsgBody")
    private String cmdMsgBody;

    @ApiModelProperty("extra")
    private String extra;
}
