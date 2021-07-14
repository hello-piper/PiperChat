package io.piper.common.pojo.message;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * 图片消息
 *
 * @author piper
 */
@Data
@Message
public class ImageMsgBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 图片宽度
     */
    private Double width;

    /**
     * 图片高度
     */
    private Double height;

    /**
     * 图片大小(kb)
     */
    private Long size;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 图片缩略图地址
     */
    private String thumbnailUrl;

    public ImageMsgBody() {
    }

    public ImageMsgBody(Double width, Double height, Long size, String imgUrl, String thumbnailUrl) {
        this.width = width;
        this.height = height;
        this.size = size;
        this.imgUrl = imgUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
