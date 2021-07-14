package io.piper.common.pojo.message;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * 位置消息
 *
 * @author piper
 */
@Data
@Message
public class LocationMsgBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 地址
     */
    public String address;

    /**
     * 维度
     */
    public Double latitude;

    /**
     * 经度
     */
    public Double longitude;

    public LocationMsgBody() {
    }

    public LocationMsgBody(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
