package com.example.mapper.impl;

import com.example.dto.admin.AdminTopicRes;
import com.example.dto.user.UserTopicRes;
import com.example.entity.Topic;
import com.example.mapper.CourseMapper;
import com.example.mapper.TopicMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author BAO 7/19/2023
 */
@Component
@AllArgsConstructor
public class TopicMapperImpl implements TopicMapper {

  private final CourseMapper courseMapper;

  @Override
  public AdminTopicRes topicToAdminTopicRes(Topic topic) {
    AdminTopicRes adminTopicRes = new AdminTopicRes();
    adminTopicRes.setId(topic.getId());
    adminTopicRes.setTitleEn(topic.getTitleEn());
    adminTopicRes.setTitleVn(topic.getTitleVn());
    adminTopicRes.setNumberWords(topic.getVocabulary().size());
    adminTopicRes.setCourse(courseMapper.courseToAdminCourseRes(topic.getCourse()));
    adminTopicRes.setImg(topic.getImg());
    return adminTopicRes;
  }

  @Override
  public UserTopicRes topicToUserTopicRes(Topic topic) {
    UserTopicRes userTopicRes = new UserTopicRes();
    userTopicRes.setId(topic.getId());
    userTopicRes.setTitleEn(topic.getTitleEn());
    userTopicRes.setTitleVn(topic.getTitleEn());
    userTopicRes.setImg(topic.getImg());
    return userTopicRes;
  }

  @Override
  public List<UserTopicRes> topicsToTopicsUserRes(List<Topic> topics) {
    return topics.stream().map(this::topicToUserTopicRes).toList();
  }

  @Override
  public List<AdminTopicRes> topicsToAdminTopicsRes(List<Topic> topics) {
    return topics.stream().map(this::topicToAdminTopicRes).toList();
  }
}
