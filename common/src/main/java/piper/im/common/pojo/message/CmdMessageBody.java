package piper.im.common.pojo.message;

import piper.im.common.enums.MessageTypeEnum;

import java.util.Map;

/**
 * 信令消息
 *
 * @author piper
 */
public class CmdMessageBody extends MessageBody {

    /**
     * 指令
     */
    private String action;

    /**
     * 参数
     */
    private Map<String, String> params;

    public CmdMessageBody() {
    }

    public CmdMessageBody(String action, Map<String, String> params) {
        this.action = action;
        this.params = params;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public MessageTypeEnum getType() {
        return MessageTypeEnum.COMMAND;
    }
}
