package io.piper.common.pojo.message;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * 视频消息
 *
 * @author piper
 */
@Data
@Message
public class VideoMsgBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 视频地址
     */
    private String videoUrl;

    /**
     * 视频时长(s)
     */
    private Integer length;

    /**
     * 视频大小(kb)
     */
    private Long size;

    public VideoMsgBody() {
    }

    public VideoMsgBody(String videoName, String videoUrl, Integer length, Long size) {
        this.videoName = videoName;
        this.videoUrl = videoUrl;
        this.length = length;
        this.size = size;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
