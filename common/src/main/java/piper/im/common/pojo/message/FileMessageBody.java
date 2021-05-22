//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package piper.im.common.pojo.message;

import java.io.Serializable;

/**
 * 文件消息
 *
 * @author piper
 */
public class FileMessageBody implements Serializable {
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
    private long size;

    public FileMessageBody() {
    }

    public FileMessageBody(String fileName, String fileUrl, long size) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
