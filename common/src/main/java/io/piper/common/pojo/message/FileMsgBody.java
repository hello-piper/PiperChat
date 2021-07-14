package io.piper.common.pojo.message;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * 文件消息
 *
 * @author piper
 */
@Data
@Message
public class FileMsgBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件地址
     */
    private String fileUrl;

    /**
     * 文件大小(kb)
     */
    private Long size;

    public FileMsgBody() {
    }

    public FileMsgBody(String fileName, String fileUrl, Long size) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.size = size;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
