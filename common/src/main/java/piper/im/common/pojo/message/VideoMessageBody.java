//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package piper.im.common.pojo.message;

import java.io.Serializable;

/**
 * 视频消息
 *
 * @author piper
 */
public class VideoMessageBody implements Serializable {
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
    private int length;

    /**
     * 视频大小(kb)
     */
    private long size;

    public VideoMessageBody() {
    }

    public VideoMessageBody(String videoName, String videoUrl, int length, long size) {
        this.videoName = videoName;
        this.videoUrl = videoUrl;
        this.length = length;
        this.size = size;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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
