package piper.im.netty;

import io.netty.channel.Channel;
import piper.im.common.WebSocketUser;
import piper.im.common.enums.ChatTypeEnum;
import piper.im.common.pojo.message.Msg;

import java.util.List;
import java.util.Objects;

/**
 * 消息处理
 *
 * @author piper
 * @date 2021-01-24 20:04
 */
public class MessageHandler {

    /**
     * 处理消息
     *
     * @param dto
     */
    public static void send(Msg dto) {
        ChatTypeEnum chatTypeEnum = ChatTypeEnum.valueOf(dto.getChatType());
        if (Objects.isNull(chatTypeEnum)) {
            throw new UnsupportedOperationException("不支持的类型");
        }

        switch (chatTypeEnum) {
            case SINGLE:
                Channel channel = WebSocketUser.get(dto.getTo());
                if (channel != null) {
                    channel.writeAndFlush(dto.toString());
                }
                break;
            case GROUP:
            case CHATROOM:
                List<Channel> channels = WebSocketUser.getGroupChannels(dto.getTo());
                if (channels != null) {
                    channels.forEach(v -> v.writeAndFlush(dto.toString()));
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + chatTypeEnum);
        }
    }

}
