package com.example.service.impl;

import com.example.config.PropertiesConfig;
import com.example.dto.admin.AdminTopicRes;
import com.example.dto.user.UserTopicRes;
import com.example.entity.Topic;
import com.example.exception.ApiRequestException;
import com.example.mapper.TopicMapper;
import com.example.repository.TopicRepository;
import com.example.service.TopicService;

import java.io.IOException;
import java.util.List;

import com.example.utils.FileUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author BAO 7/7/2023
 */
@Service
@AllArgsConstructor
public class TopicServiceImpl implements TopicService {

  private final TopicRepository topicRepository;
  private final TopicMapper topicMapper;
  private final FileUtils fileUtils;
  private final PropertiesConfig env;

  @Override
  public Topic save(Topic topic, MultipartFile img) {
    Topic savedTopic = topicRepository.save(topic);
    try {
      String fileName = fileUtils.saveFile(img, topic.getId(), env.getPathImgTopic(), "topic");
      savedTopic.setImg(fileName);
      savedTopic = topicRepository.save(savedTopic);
    } catch (IOException e) {
      throw new ApiRequestException(e, e.getMessage());
    }
    return savedTopic;
  }

  @Transactional
  @Override
  public List<AdminTopicRes> adminFindByCourseId(int courseId) {
    List<Topic> topics = topicRepository.findByCourseId(courseId);
    return topicMapper.topicsToAdminTopicsRes(topics);
  }

  @Override
  public List<AdminTopicRes> listAll() {
    List<Topic> topicResponses = topicRepository.findAll();
    return topicMapper.topicsToAdminTopicsRes(topicResponses);
  }

  @Override
  public void deleteTopicById(int id) {
    topicRepository.deleteById(id);
    if (topicRepository.existsById(id)) throw new ApiRequestException("Something went wrong");
  }

  @Override
  public List<UserTopicRes> userGetTopics(int courseId) {
    List<Topic> topics = topicRepository.findByCourseId(courseId);
    return topicMapper.topicsToTopicsUserRes(topics);
  }
}
