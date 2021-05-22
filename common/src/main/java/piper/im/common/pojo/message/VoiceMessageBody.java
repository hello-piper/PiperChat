//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package piper.im.common.pojo.message;

import java.io.Serializable;

/**
 * 语言消息
 *
 * @author piper
 */
public class VoiceMessageBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 语言地址
     */
    private String voiceUrl;

    /**
     * 语言时长(s)
     */
    private int length;

    /**
     * 语言大小(kb)
     */
    private long size;

    public VoiceMessageBody() {
    }

    public VoiceMessageBody(String voiceUrl, int length, long size) {
        this.voiceUrl = voiceUrl;
        this.length = length;
        this.size = size;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
