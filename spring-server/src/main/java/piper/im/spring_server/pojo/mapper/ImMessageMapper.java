package piper.im.spring_server.pojo.mapper;

import piper.im.spring_server.pojo.entity.ImMessage;
import piper.im.spring_server.pojo.entity.ImMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImMessageMapper {
    long countByExample(ImMessageExample example);

    int deleteByExample(ImMessageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ImMessage record);

    int insertSelective(ImMessage record);

    List<ImMessage> selectByExample(ImMessageExample example);

    ImMessage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ImMessage record, @Param("example") ImMessageExample example);

    int updateByExample(@Param("record") ImMessage record, @Param("example") ImMessageExample example);

    int updateByPrimaryKeySelective(ImMessage record);

    int updateByPrimaryKey(ImMessage record);
}