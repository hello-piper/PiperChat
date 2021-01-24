package piper.im.common;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 消息DTO
 *
 * @author piper
 */
public class MessageDTO implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 消息操作类型
     * 0：点对点个人消息，1：群消息（高级群），其他返回414
     */
    private byte ope;

    /**
     * 发送者accid，用户帐号，最大32字符，
     * 必须保证一个APP内唯一
     */
    private String from;

    /**
     * 接收者
     * ope==0是表示accid即用户id，ope==1表示tid即群id
     */
    private String to;

    /**
     * 消息内容
     */
    private String body;

    public byte getOpe() {
        return ope;
    }

    public void setOpe(byte ope) {
        this.ope = ope;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
