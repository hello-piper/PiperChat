//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package piper.im.common.pojo.message;

import piper.im.common.enums.MessageTypeEnum;

/**
 * 图片消息
 *
 * @author piper
 */
public class ImageMessageBody extends MessageBody {

    /**
     * 图片宽度
     */
    private double width;

    /**
     * 图片高度
     */
    private double height;

    /**
     * 图片大小(kb)
     */
    private long size;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 图片缩略图地址
     */
    private String thumbnailUrl;

    public ImageMessageBody() {
    }

    public ImageMessageBody(double width, double height, long size, String imgUrl, String thumbnailUrl) {
        this.width = width;
        this.height = height;
        this.size = size;
        this.imgUrl = imgUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public MessageTypeEnum getType() {
        return MessageTypeEnum.IMAGE;
    }
}
