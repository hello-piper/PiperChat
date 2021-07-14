package io.piper.common.pojo.message;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * 语言消息
 *
 * @author piper
 */
@Data
@Message
public class VoiceMsgBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 语言地址
     */
    private String voiceUrl;

    /**
     * 语言时长(s)
     */
    private Integer length;

    /**
     * 语言大小(kb)
     */
    private Long size;

    public VoiceMsgBody() {
    }

    public VoiceMsgBody(String voiceUrl, Integer length, Long size) {
        this.voiceUrl = voiceUrl;
        this.length = length;
        this.size = size;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
