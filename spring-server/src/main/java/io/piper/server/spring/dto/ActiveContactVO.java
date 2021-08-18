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
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("ActiveContactVO")
public class ActiveContactVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("会话id")
    private String conversationId;

    @ApiModelProperty("uid/groupId")
    private Long to;

    @ApiModelProperty("name")
    private String name;

    @ApiModelProperty("avatar")
    private String avatar;

    @ApiModelProperty("chatType")
    private Byte chatType;

    @ApiModelProperty("messageList")
    private List<ImMessageDTO> messageList;

    public void addMessageDTO(ImMessageDTO dto) {
        if (this.messageList == null) {
            this.messageList = new ArrayList<>();
        }
        this.messageList.add(dto);
    }
}
