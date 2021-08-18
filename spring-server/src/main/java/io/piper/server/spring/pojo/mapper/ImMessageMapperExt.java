package io.piper.server.spring.pojo.mapper;

import io.piper.server.spring.dto.ImMessageDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImMessageMapperExt extends ImMessageMapper {

    List<ImMessageDTO> activeContacts();

    List<ImMessageDTO> selectMessage(@Param("lastMsgId") Long lastMsgId, @Param("conversationId") String conversationId, @Param("total") Integer total);
}
