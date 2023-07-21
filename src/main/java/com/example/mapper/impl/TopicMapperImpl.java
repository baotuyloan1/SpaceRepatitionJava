package com.example.mapper.impl;

import com.example.dto.admin.AdminTopicRes;
import com.example.dto.user.UserTopicRes;
import com.example.entity.Topic;
import com.example.mapper.TopicMapper;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * @author BAO 7/19/2023
 */
@Component
public class TopicMapperImpl implements TopicMapper {
  @Override
  public UserTopicRes topicToUserTopicRes(Topic topic) {
    UserTopicRes userTopicRes = new UserTopicRes();
    userTopicRes.setId(topic.getId());
    userTopicRes.setTitleEn(topic.getTitleEn());
    userTopicRes.setTitleVn(topic.getTitleEn());
    return userTopicRes;
  }

  @Override
  public List<UserTopicRes> topicsToTopicsUserResponse(List<Topic> topics) {
    return topics.stream().map(this::topicToUserTopicRes).toList();
  }

  @Override
  public AdminTopicRes topicToAdminTopicRes(Topic topic) {
    AdminTopicRes adminTopicRes = new AdminTopicRes();
    adminTopicRes.setId(topic.getId());
    adminTopicRes.setTitleEn(topic.getTitleEn());
    adminTopicRes.setTitleVn(topic.getTitleVn());
    adminTopicRes.setNumberWords(topic.getVocabulary().size());
    return adminTopicRes;
  }

  @Override
  public List<AdminTopicRes> topicsToAdminTopicsRes(List<Topic> topics) {
    return topics.stream().map(this::topicToAdminTopicRes).toList();
  }
}
