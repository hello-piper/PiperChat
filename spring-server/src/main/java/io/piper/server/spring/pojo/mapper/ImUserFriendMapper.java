package io.piper.server.spring.pojo.mapper;

import io.piper.server.spring.pojo.entity.ImUserFriend;
import io.piper.server.spring.pojo.entity.ImUserFriendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImUserFriendMapper {
    long countByExample(ImUserFriendExample example);

    int deleteByExample(ImUserFriendExample example);

    int deleteByPrimaryKey(String id);

    int insert(ImUserFriend record);

    int insertSelective(ImUserFriend record);

    List<ImUserFriend> selectByExample(ImUserFriendExample example);

    ImUserFriend selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ImUserFriend record, @Param("example") ImUserFriendExample example);

    int updateByExample(@Param("record") ImUserFriend record, @Param("example") ImUserFriendExample example);

    int updateByPrimaryKeySelective(ImUserFriend record);

    int updateByPrimaryKey(ImUserFriend record);
}