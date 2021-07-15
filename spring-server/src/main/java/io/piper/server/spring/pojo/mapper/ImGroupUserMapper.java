package io.piper.server.spring.pojo.mapper;

import io.piper.server.spring.pojo.entity.ImGroupUser;
import io.piper.server.spring.pojo.entity.ImGroupUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImGroupUserMapper {
    long countByExample(ImGroupUserExample example);

    int deleteByExample(ImGroupUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ImGroupUser record);

    int insertSelective(ImGroupUser record);

    List<ImGroupUser> selectByExample(ImGroupUserExample example);

    ImGroupUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ImGroupUser record, @Param("example") ImGroupUserExample example);

    int updateByExample(@Param("record") ImGroupUser record, @Param("example") ImGroupUserExample example);

    int updateByPrimaryKeySelective(ImGroupUser record);

    int updateByPrimaryKey(ImGroupUser record);
}