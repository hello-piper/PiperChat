package piper.im.common.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 聊天消息 po
 *
 * @author piper
 */
@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 1;

    private Long id;

    private Byte chatType;

    private Byte messageType;

    private String from;

    private String to;

    private String body;

    private Long createTime;
}
