package io.piper.server.spring.pojo.entity;

import java.util.ArrayList;
import java.util.List;

public class ImMessageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ImMessageExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andChatTypeIsNull() {
            addCriterion("chat_type is null");
            return (Criteria) this;
        }

        public Criteria andChatTypeIsNotNull() {
            addCriterion("chat_type is not null");
            return (Criteria) this;
        }

        public Criteria andChatTypeEqualTo(Byte value) {
            addCriterion("chat_type =", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotEqualTo(Byte value) {
            addCriterion("chat_type <>", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeGreaterThan(Byte value) {
            addCriterion("chat_type >", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("chat_type >=", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeLessThan(Byte value) {
            addCriterion("chat_type <", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeLessThanOrEqualTo(Byte value) {
            addCriterion("chat_type <=", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeIn(List<Byte> values) {
            addCriterion("chat_type in", values, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotIn(List<Byte> values) {
            addCriterion("chat_type not in", values, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeBetween(Byte value1, Byte value2) {
            addCriterion("chat_type between", value1, value2, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("chat_type not between", value1, value2, "chatType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeIsNull() {
            addCriterion("msg_type is null");
            return (Criteria) this;
        }

        public Criteria andMsgTypeIsNotNull() {
            addCriterion("msg_type is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTypeEqualTo(Byte value) {
            addCriterion("msg_type =", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeNotEqualTo(Byte value) {
            addCriterion("msg_type <>", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeGreaterThan(Byte value) {
            addCriterion("msg_type >", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("msg_type >=", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeLessThan(Byte value) {
            addCriterion("msg_type <", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeLessThanOrEqualTo(Byte value) {
            addCriterion("msg_type <=", value, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeIn(List<Byte> values) {
            addCriterion("msg_type in", values, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeNotIn(List<Byte> values) {
            addCriterion("msg_type not in", values, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeBetween(Byte value1, Byte value2) {
            addCriterion("msg_type between", value1, value2, "msgType");
            return (Criteria) this;
        }

        public Criteria andMsgTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("msg_type not between", value1, value2, "msgType");
            return (Criteria) this;
        }

        public Criteria andFromIsNull() {
            addCriterion("`from` is null");
            return (Criteria) this;
        }

        public Criteria andFromIsNotNull() {
            addCriterion("`from` is not null");
            return (Criteria) this;
        }

        public Criteria andFromEqualTo(Long value) {
            addCriterion("`from` =", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromNotEqualTo(Long value) {
            addCriterion("`from` <>", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromGreaterThan(Long value) {
            addCriterion("`from` >", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromGreaterThanOrEqualTo(Long value) {
            addCriterion("`from` >=", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromLessThan(Long value) {
            addCriterion("`from` <", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromLessThanOrEqualTo(Long value) {
            addCriterion("`from` <=", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromIn(List<Long> values) {
            addCriterion("`from` in", values, "from");
            return (Criteria) this;
        }

        public Criteria andFromNotIn(List<Long> values) {
            addCriterion("`from` not in", values, "from");
            return (Criteria) this;
        }

        public Criteria andFromBetween(Long value1, Long value2) {
            addCriterion("`from` between", value1, value2, "from");
            return (Criteria) this;
        }

        public Criteria andFromNotBetween(Long value1, Long value2) {
            addCriterion("`from` not between", value1, value2, "from");
            return (Criteria) this;
        }

        public Criteria andToIsNull() {
            addCriterion("`to` is null");
            return (Criteria) this;
        }

        public Criteria andToIsNotNull() {
            addCriterion("`to` is not null");
            return (Criteria) this;
        }

        public Criteria andToEqualTo(Long value) {
            addCriterion("`to` =", value, "to");
            return (Criteria) this;
        }

        public Criteria andToNotEqualTo(Long value) {
            addCriterion("`to` <>", value, "to");
            return (Criteria) this;
        }

        public Criteria andToGreaterThan(Long value) {
            addCriterion("`to` >", value, "to");
            return (Criteria) this;
        }

        public Criteria andToGreaterThanOrEqualTo(Long value) {
            addCriterion("`to` >=", value, "to");
            return (Criteria) this;
        }

        public Criteria andToLessThan(Long value) {
            addCriterion("`to` <", value, "to");
            return (Criteria) this;
        }

        public Criteria andToLessThanOrEqualTo(Long value) {
            addCriterion("`to` <=", value, "to");
            return (Criteria) this;
        }

        public Criteria andToIn(List<Long> values) {
            addCriterion("`to` in", values, "to");
            return (Criteria) this;
        }

        public Criteria andToNotIn(List<Long> values) {
            addCriterion("`to` not in", values, "to");
            return (Criteria) this;
        }

        public Criteria andToBetween(Long value1, Long value2) {
            addCriterion("`to` between", value1, value2, "to");
            return (Criteria) this;
        }

        public Criteria andToNotBetween(Long value1, Long value2) {
            addCriterion("`to` not between", value1, value2, "to");
            return (Criteria) this;
        }

        public Criteria andConversationIdIsNull() {
            addCriterion("conversation_id is null");
            return (Criteria) this;
        }

        public Criteria andConversationIdIsNotNull() {
            addCriterion("conversation_id is not null");
            return (Criteria) this;
        }

        public Criteria andConversationIdEqualTo(String value) {
            addCriterion("conversation_id =", value, "conversationId");
            return (Criteria) this;
        }

        public Criteria andConversationIdNotEqualTo(String value) {
            addCriterion("conversation_id <>", value, "conversationId");
            return (Criteria) this;
        }

        public Criteria andConversationIdGreaterThan(String value) {
            addCriterion("conversation_id >", value, "conversationId");
            return (Criteria) this;
        }

        public Criteria andConversationIdGreaterThanOrEqualTo(String value) {
            addCriterion("conversation_id >=", value, "conversationId");
            return (Criteria) this;
        }

        public Criteria andConversationIdLessThan(String value) {
            addCriterion("conversation_id <", value, "conversationId");
            return (Criteria) this;
        }

        public Criteria andConversationIdLessThanOrEqualTo(String value) {
            addCriterion("conversation_id <=", value, "conversationId");
            return (Criteria) this;
        }

        public Criteria andConversationIdLike(String value) {
            addCriterion("conversation_id like", value, "conversationId");
            return (Criteria) this;
        }

        public Criteria andConversationIdNotLike(String value) {
            addCriterion("conversation_id not like", value, "conversationId");
            return (Criteria) this;
        }

        public Criteria andConversationIdIn(List<String> values) {
            addCriterion("conversation_id in", values, "conversationId");
            return (Criteria) this;
        }

        public Criteria andConversationIdNotIn(List<String> values) {
            addCriterion("conversation_id not in", values, "conversationId");
            return (Criteria) this;
        }

        public Criteria andConversationIdBetween(String value1, String value2) {
            addCriterion("conversation_id between", value1, value2, "conversationId");
            return (Criteria) this;
        }

        public Criteria andConversationIdNotBetween(String value1, String value2) {
            addCriterion("conversation_id not between", value1, value2, "conversationId");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNull() {
            addCriterion("send_time is null");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNotNull() {
            addCriterion("send_time is not null");
            return (Criteria) this;
        }

        public Criteria andSendTimeEqualTo(Long value) {
            addCriterion("send_time =", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotEqualTo(Long value) {
            addCriterion("send_time <>", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThan(Long value) {
            addCriterion("send_time >", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("send_time >=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThan(Long value) {
            addCriterion("send_time <", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThanOrEqualTo(Long value) {
            addCriterion("send_time <=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIn(List<Long> values) {
            addCriterion("send_time in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotIn(List<Long> values) {
            addCriterion("send_time not in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeBetween(Long value1, Long value2) {
            addCriterion("send_time between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotBetween(Long value1, Long value2) {
            addCriterion("send_time not between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeIsNull() {
            addCriterion("server_time is null");
            return (Criteria) this;
        }

        public Criteria andServerTimeIsNotNull() {
            addCriterion("server_time is not null");
            return (Criteria) this;
        }

        public Criteria andServerTimeEqualTo(Long value) {
            addCriterion("server_time =", value, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeNotEqualTo(Long value) {
            addCriterion("server_time <>", value, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeGreaterThan(Long value) {
            addCriterion("server_time >", value, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("server_time >=", value, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeLessThan(Long value) {
            addCriterion("server_time <", value, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeLessThanOrEqualTo(Long value) {
            addCriterion("server_time <=", value, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeIn(List<Long> values) {
            addCriterion("server_time in", values, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeNotIn(List<Long> values) {
            addCriterion("server_time not in", values, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeBetween(Long value1, Long value2) {
            addCriterion("server_time between", value1, value2, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeNotBetween(Long value1, Long value2) {
            addCriterion("server_time not between", value1, value2, "serverTime");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyIsNull() {
            addCriterion("image_msg_body is null");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyIsNotNull() {
            addCriterion("image_msg_body is not null");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyEqualTo(String value) {
            addCriterion("image_msg_body =", value, "imageMsgBody");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyNotEqualTo(String value) {
            addCriterion("image_msg_body <>", value, "imageMsgBody");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyGreaterThan(String value) {
            addCriterion("image_msg_body >", value, "imageMsgBody");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyGreaterThanOrEqualTo(String value) {
            addCriterion("image_msg_body >=", value, "imageMsgBody");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyLessThan(String value) {
            addCriterion("image_msg_body <", value, "imageMsgBody");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyLessThanOrEqualTo(String value) {
            addCriterion("image_msg_body <=", value, "imageMsgBody");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyLike(String value) {
            addCriterion("image_msg_body like", value, "imageMsgBody");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyNotLike(String value) {
            addCriterion("image_msg_body not like", value, "imageMsgBody");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyIn(List<String> values) {
            addCriterion("image_msg_body in", values, "imageMsgBody");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyNotIn(List<String> values) {
            addCriterion("image_msg_body not in", values, "imageMsgBody");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyBetween(String value1, String value2) {
            addCriterion("image_msg_body between", value1, value2, "imageMsgBody");
            return (Criteria) this;
        }

        public Criteria andImageMsgBodyNotBetween(String value1, String value2) {
            addCriterion("image_msg_body not between", value1, value2, "imageMsgBody");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyIsNull() {
            addCriterion("voice_msg_body is null");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyIsNotNull() {
            addCriterion("voice_msg_body is not null");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyEqualTo(String value) {
            addCriterion("voice_msg_body =", value, "voiceMsgBody");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyNotEqualTo(String value) {
            addCriterion("voice_msg_body <>", value, "voiceMsgBody");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyGreaterThan(String value) {
            addCriterion("voice_msg_body >", value, "voiceMsgBody");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyGreaterThanOrEqualTo(String value) {
            addCriterion("voice_msg_body >=", value, "voiceMsgBody");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyLessThan(String value) {
            addCriterion("voice_msg_body <", value, "voiceMsgBody");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyLessThanOrEqualTo(String value) {
            addCriterion("voice_msg_body <=", value, "voiceMsgBody");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyLike(String value) {
            addCriterion("voice_msg_body like", value, "voiceMsgBody");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyNotLike(String value) {
            addCriterion("voice_msg_body not like", value, "voiceMsgBody");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyIn(List<String> values) {
            addCriterion("voice_msg_body in", values, "voiceMsgBody");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyNotIn(List<String> values) {
            addCriterion("voice_msg_body not in", values, "voiceMsgBody");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyBetween(String value1, String value2) {
            addCriterion("voice_msg_body between", value1, value2, "voiceMsgBody");
            return (Criteria) this;
        }

        public Criteria andVoiceMsgBodyNotBetween(String value1, String value2) {
            addCriterion("voice_msg_body not between", value1, value2, "voiceMsgBody");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyIsNull() {
            addCriterion("video_msg_body is null");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyIsNotNull() {
            addCriterion("video_msg_body is not null");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyEqualTo(String value) {
            addCriterion("video_msg_body =", value, "videoMsgBody");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyNotEqualTo(String value) {
            addCriterion("video_msg_body <>", value, "videoMsgBody");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyGreaterThan(String value) {
            addCriterion("video_msg_body >", value, "videoMsgBody");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyGreaterThanOrEqualTo(String value) {
            addCriterion("video_msg_body >=", value, "videoMsgBody");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyLessThan(String value) {
            addCriterion("video_msg_body <", value, "videoMsgBody");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyLessThanOrEqualTo(String value) {
            addCriterion("video_msg_body <=", value, "videoMsgBody");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyLike(String value) {
            addCriterion("video_msg_body like", value, "videoMsgBody");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyNotLike(String value) {
            addCriterion("video_msg_body not like", value, "videoMsgBody");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyIn(List<String> values) {
            addCriterion("video_msg_body in", values, "videoMsgBody");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyNotIn(List<String> values) {
            addCriterion("video_msg_body not in", values, "videoMsgBody");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyBetween(String value1, String value2) {
            addCriterion("video_msg_body between", value1, value2, "videoMsgBody");
            return (Criteria) this;
        }

        public Criteria andVideoMsgBodyNotBetween(String value1, String value2) {
            addCriterion("video_msg_body not between", value1, value2, "videoMsgBody");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyIsNull() {
            addCriterion("file_msg_body is null");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyIsNotNull() {
            addCriterion("file_msg_body is not null");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyEqualTo(String value) {
            addCriterion("file_msg_body =", value, "fileMsgBody");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyNotEqualTo(String value) {
            addCriterion("file_msg_body <>", value, "fileMsgBody");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyGreaterThan(String value) {
            addCriterion("file_msg_body >", value, "fileMsgBody");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyGreaterThanOrEqualTo(String value) {
            addCriterion("file_msg_body >=", value, "fileMsgBody");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyLessThan(String value) {
            addCriterion("file_msg_body <", value, "fileMsgBody");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyLessThanOrEqualTo(String value) {
            addCriterion("file_msg_body <=", value, "fileMsgBody");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyLike(String value) {
            addCriterion("file_msg_body like", value, "fileMsgBody");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyNotLike(String value) {
            addCriterion("file_msg_body not like", value, "fileMsgBody");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyIn(List<String> values) {
            addCriterion("file_msg_body in", values, "fileMsgBody");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyNotIn(List<String> values) {
            addCriterion("file_msg_body not in", values, "fileMsgBody");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyBetween(String value1, String value2) {
            addCriterion("file_msg_body between", value1, value2, "fileMsgBody");
            return (Criteria) this;
        }

        public Criteria andFileMsgBodyNotBetween(String value1, String value2) {
            addCriterion("file_msg_body not between", value1, value2, "fileMsgBody");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyIsNull() {
            addCriterion("location_msg_body is null");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyIsNotNull() {
            addCriterion("location_msg_body is not null");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyEqualTo(String value) {
            addCriterion("location_msg_body =", value, "locationMsgBody");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyNotEqualTo(String value) {
            addCriterion("location_msg_body <>", value, "locationMsgBody");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyGreaterThan(String value) {
            addCriterion("location_msg_body >", value, "locationMsgBody");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyGreaterThanOrEqualTo(String value) {
            addCriterion("location_msg_body >=", value, "locationMsgBody");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyLessThan(String value) {
            addCriterion("location_msg_body <", value, "locationMsgBody");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyLessThanOrEqualTo(String value) {
            addCriterion("location_msg_body <=", value, "locationMsgBody");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyLike(String value) {
            addCriterion("location_msg_body like", value, "locationMsgBody");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyNotLike(String value) {
            addCriterion("location_msg_body not like", value, "locationMsgBody");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyIn(List<String> values) {
            addCriterion("location_msg_body in", values, "locationMsgBody");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyNotIn(List<String> values) {
            addCriterion("location_msg_body not in", values, "locationMsgBody");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyBetween(String value1, String value2) {
            addCriterion("location_msg_body between", value1, value2, "locationMsgBody");
            return (Criteria) this;
        }

        public Criteria andLocationMsgBodyNotBetween(String value1, String value2) {
            addCriterion("location_msg_body not between", value1, value2, "locationMsgBody");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyIsNull() {
            addCriterion("cmd_msg_body is null");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyIsNotNull() {
            addCriterion("cmd_msg_body is not null");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyEqualTo(String value) {
            addCriterion("cmd_msg_body =", value, "cmdMsgBody");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyNotEqualTo(String value) {
            addCriterion("cmd_msg_body <>", value, "cmdMsgBody");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyGreaterThan(String value) {
            addCriterion("cmd_msg_body >", value, "cmdMsgBody");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyGreaterThanOrEqualTo(String value) {
            addCriterion("cmd_msg_body >=", value, "cmdMsgBody");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyLessThan(String value) {
            addCriterion("cmd_msg_body <", value, "cmdMsgBody");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyLessThanOrEqualTo(String value) {
            addCriterion("cmd_msg_body <=", value, "cmdMsgBody");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyLike(String value) {
            addCriterion("cmd_msg_body like", value, "cmdMsgBody");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyNotLike(String value) {
            addCriterion("cmd_msg_body not like", value, "cmdMsgBody");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyIn(List<String> values) {
            addCriterion("cmd_msg_body in", values, "cmdMsgBody");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyNotIn(List<String> values) {
            addCriterion("cmd_msg_body not in", values, "cmdMsgBody");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyBetween(String value1, String value2) {
            addCriterion("cmd_msg_body between", value1, value2, "cmdMsgBody");
            return (Criteria) this;
        }

        public Criteria andCmdMsgBodyNotBetween(String value1, String value2) {
            addCriterion("cmd_msg_body not between", value1, value2, "cmdMsgBody");
            return (Criteria) this;
        }

        public Criteria andExtraIsNull() {
            addCriterion("extra is null");
            return (Criteria) this;
        }

        public Criteria andExtraIsNotNull() {
            addCriterion("extra is not null");
            return (Criteria) this;
        }

        public Criteria andExtraEqualTo(String value) {
            addCriterion("extra =", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraNotEqualTo(String value) {
            addCriterion("extra <>", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraGreaterThan(String value) {
            addCriterion("extra >", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraGreaterThanOrEqualTo(String value) {
            addCriterion("extra >=", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraLessThan(String value) {
            addCriterion("extra <", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraLessThanOrEqualTo(String value) {
            addCriterion("extra <=", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraLike(String value) {
            addCriterion("extra like", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraNotLike(String value) {
            addCriterion("extra not like", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraIn(List<String> values) {
            addCriterion("extra in", values, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraNotIn(List<String> values) {
            addCriterion("extra not in", values, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraBetween(String value1, String value2) {
            addCriterion("extra between", value1, value2, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraNotBetween(String value1, String value2) {
            addCriterion("extra not between", value1, value2, "extra");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}