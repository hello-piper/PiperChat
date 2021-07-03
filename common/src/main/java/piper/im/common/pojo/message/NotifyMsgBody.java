package piper.im.common.pojo.message;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.msgpack.annotation.Message;

import java.io.Serializable;
import java.util.Map;

/**
 * 通知消息
 *
 * @author piper
 */
@Data
@Message
public class NotifyMsgBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 通知类型
     */
    private String type;

    /**
     * 参数
     */
    private Map<String, String> params;

    public NotifyMsgBody() {
    }

    public NotifyMsgBody(String type, Map<String, String> params) {
        this.type = type;
        this.params = params;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
