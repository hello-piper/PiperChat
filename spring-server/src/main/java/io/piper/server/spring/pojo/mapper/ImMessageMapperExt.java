package io.piper.server.spring.pojo.mapper;

import io.piper.server.spring.dto.ImMessageDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImMessageMapperExt extends ImMessageMapper {

    List<ImMessageDTO> selectMessage(@Param("lastMsgId") Long lastMsgId, @Param("from") String from, @Param("to") String to, @Param("total") Integer total);
}
