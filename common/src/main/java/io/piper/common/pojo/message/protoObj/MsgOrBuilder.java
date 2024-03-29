// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/Msg.proto

// Protobuf Java Version: 3.25.3
package io.piper.common.pojo.message.protoObj;

public interface MsgOrBuilder extends
    // @@protoc_insertion_point(interface_extends:protoObj.Msg)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 id = 1;</code>
   * @return The id.
   */
  long getId();

  /**
   * <code>int32 type = 2;</code>
   * @return The type.
   */
  int getType();

  /**
   * <code>int32 chatType = 3;</code>
   * @return The chatType.
   */
  int getChatType();

  /**
   * <code>int64 chatId = 4;</code>
   * @return The chatId.
   */
  long getChatId();

  /**
   * <code>int64 from = 5;</code>
   * @return The from.
   */
  long getFrom();

  /**
   * <code>repeated int64 to = 6;</code>
   * @return A list containing the to.
   */
  java.util.List<java.lang.Long> getToList();
  /**
   * <code>repeated int64 to = 6;</code>
   * @return The count of to.
   */
  int getToCount();
  /**
   * <code>repeated int64 to = 6;</code>
   * @param index The index of the element to return.
   * @return The to at the given index.
   */
  long getTo(int index);

  /**
   * <code>string text = 7;</code>
   * @return The text.
   */
  java.lang.String getText();
  /**
   * <code>string text = 7;</code>
   * @return The bytes for text.
   */
  com.google.protobuf.ByteString
      getTextBytes();

  /**
   * <code>int64 time = 8;</code>
   * @return The time.
   */
  long getTime();

  /**
   * <code>.protoObj.ImageMsgBody imageMsgBody = 9;</code>
   * @return Whether the imageMsgBody field is set.
   */
  boolean hasImageMsgBody();
  /**
   * <code>.protoObj.ImageMsgBody imageMsgBody = 9;</code>
   * @return The imageMsgBody.
   */
  io.piper.common.pojo.message.protoObj.ImageMsgBody getImageMsgBody();
  /**
   * <code>.protoObj.ImageMsgBody imageMsgBody = 9;</code>
   */
  io.piper.common.pojo.message.protoObj.ImageMsgBodyOrBuilder getImageMsgBodyOrBuilder();

  /**
   * <code>.protoObj.AudioMsgBody audioMsgBody = 10;</code>
   * @return Whether the audioMsgBody field is set.
   */
  boolean hasAudioMsgBody();
  /**
   * <code>.protoObj.AudioMsgBody audioMsgBody = 10;</code>
   * @return The audioMsgBody.
   */
  io.piper.common.pojo.message.protoObj.AudioMsgBody getAudioMsgBody();
  /**
   * <code>.protoObj.AudioMsgBody audioMsgBody = 10;</code>
   */
  io.piper.common.pojo.message.protoObj.AudioMsgBodyOrBuilder getAudioMsgBodyOrBuilder();

  /**
   * <code>.protoObj.VideoMsgBody videoMsgBody = 11;</code>
   * @return Whether the videoMsgBody field is set.
   */
  boolean hasVideoMsgBody();
  /**
   * <code>.protoObj.VideoMsgBody videoMsgBody = 11;</code>
   * @return The videoMsgBody.
   */
  io.piper.common.pojo.message.protoObj.VideoMsgBody getVideoMsgBody();
  /**
   * <code>.protoObj.VideoMsgBody videoMsgBody = 11;</code>
   */
  io.piper.common.pojo.message.protoObj.VideoMsgBodyOrBuilder getVideoMsgBodyOrBuilder();

  /**
   * <code>.protoObj.FileMsgBody fileMsgBody = 12;</code>
   * @return Whether the fileMsgBody field is set.
   */
  boolean hasFileMsgBody();
  /**
   * <code>.protoObj.FileMsgBody fileMsgBody = 12;</code>
   * @return The fileMsgBody.
   */
  io.piper.common.pojo.message.protoObj.FileMsgBody getFileMsgBody();
  /**
   * <code>.protoObj.FileMsgBody fileMsgBody = 12;</code>
   */
  io.piper.common.pojo.message.protoObj.FileMsgBodyOrBuilder getFileMsgBodyOrBuilder();

  /**
   * <code>.protoObj.LocationMsgBody locationMsgBody = 13;</code>
   * @return Whether the locationMsgBody field is set.
   */
  boolean hasLocationMsgBody();
  /**
   * <code>.protoObj.LocationMsgBody locationMsgBody = 13;</code>
   * @return The locationMsgBody.
   */
  io.piper.common.pojo.message.protoObj.LocationMsgBody getLocationMsgBody();
  /**
   * <code>.protoObj.LocationMsgBody locationMsgBody = 13;</code>
   */
  io.piper.common.pojo.message.protoObj.LocationMsgBodyOrBuilder getLocationMsgBodyOrBuilder();

  /**
   * <code>.protoObj.CustomMsgBody customMsgBody = 14;</code>
   * @return Whether the customMsgBody field is set.
   */
  boolean hasCustomMsgBody();
  /**
   * <code>.protoObj.CustomMsgBody customMsgBody = 14;</code>
   * @return The customMsgBody.
   */
  io.piper.common.pojo.message.protoObj.CustomMsgBody getCustomMsgBody();
  /**
   * <code>.protoObj.CustomMsgBody customMsgBody = 14;</code>
   */
  io.piper.common.pojo.message.protoObj.CustomMsgBodyOrBuilder getCustomMsgBodyOrBuilder();

  /**
   * <code>.google.protobuf.Any body = 15;</code>
   * @return Whether the body field is set.
   */
  boolean hasBody();
  /**
   * <code>.google.protobuf.Any body = 15;</code>
   * @return The body.
   */
  com.google.protobuf.Any getBody();
  /**
   * <code>.google.protobuf.Any body = 15;</code>
   */
  com.google.protobuf.AnyOrBuilder getBodyOrBuilder();
}
