package piper.im.common.pojo.entity;

import java.io.Serializable;

/**
 * 群组信息
 *
 * @author piper
 */
public class ImGroup implements Serializable {
    private static final long serialVersionUID = 1;

    private String id;
    private String groupName;
    private String owner;
    private String announcement;
    private Integer type;
    private Integer status;
    private String createUser;
    private String createTime;
}
