package piper.im.common.pojo.message;

import piper.im.common.enums.MessageTypeEnum;

import java.io.Serializable;

/**
 * 消息内容
 *
 * @author piper
 */
public abstract class MessageBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 获取消息类型
     *
     * @return
     */
    public abstract MessageTypeEnum getType();
}
