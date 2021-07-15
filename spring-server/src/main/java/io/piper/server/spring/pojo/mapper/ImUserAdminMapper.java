package io.piper.server.spring.pojo.mapper;

import io.piper.server.spring.pojo.entity.ImUserAdmin;
import io.piper.server.spring.pojo.entity.ImUserAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImUserAdminMapper {
    long countByExample(ImUserAdminExample example);

    int deleteByExample(ImUserAdminExample example);

    int deleteByPrimaryKey(Long uid);

    int insert(ImUserAdmin record);

    int insertSelective(ImUserAdmin record);

    List<ImUserAdmin> selectByExample(ImUserAdminExample example);

    ImUserAdmin selectByPrimaryKey(Long uid);

    int updateByExampleSelective(@Param("record") ImUserAdmin record, @Param("example") ImUserAdminExample example);

    int updateByExample(@Param("record") ImUserAdmin record, @Param("example") ImUserAdminExample example);

    int updateByPrimaryKeySelective(ImUserAdmin record);

    int updateByPrimaryKey(ImUserAdmin record);
}