package io.piper.server.spring.pojo.mapper;

import io.piper.server.spring.pojo.entity.ImGroup;
import io.piper.server.spring.pojo.entity.ImGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImGroupMapper {
    long countByExample(ImGroupExample example);

    int deleteByExample(ImGroupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ImGroup record);

    int insertSelective(ImGroup record);

    List<ImGroup> selectByExample(ImGroupExample example);

    ImGroup selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ImGroup record, @Param("example") ImGroupExample example);

    int updateByExample(@Param("record") ImGroup record, @Param("example") ImGroupExample example);

    int updateByPrimaryKeySelective(ImGroup record);

    int updateByPrimaryKey(ImGroup record);
}