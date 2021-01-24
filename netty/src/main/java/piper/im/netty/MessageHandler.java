package piper.im.netty;

import io.netty.channel.Channel;
import piper.im.common.MessageDTO;
import piper.im.common.MessageOpeEnum;
import piper.im.common.WebSocketUser;

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
    public static void send(MessageDTO dto) {
        MessageOpeEnum messageOpeEnum = MessageOpeEnum.valueOf(dto.getOpe());
        if (Objects.isNull(messageOpeEnum)) {
            throw new UnsupportedOperationException("不支持的类型");
        }

        switch (messageOpeEnum) {
            case peerToPeer:
                Channel channel = WebSocketUser.get(dto.getTo());
                if (channel != null) {
                    channel.writeAndFlush(dto.toString());
                }
                break;
            case group:
                List<Channel> channels = WebSocketUser.getGroupChannels(dto.getTo());
                if (channels != null) {
                    channels.forEach(v -> v.writeAndFlush(dto.toString()));
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + messageOpeEnum);
        }
    }

}
