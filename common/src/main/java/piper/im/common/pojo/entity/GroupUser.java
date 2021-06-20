package piper.im.common.pojo.entity;

import java.io.Serializable;

/**
 * 群组成员信息
 *
 * @author piper
 */
public class GroupUser implements Serializable {
    private static final long serialVersionUID = 1;

    private String groupId;
    private String userId;
    private Integer type;
}
