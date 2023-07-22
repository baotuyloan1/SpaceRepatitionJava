package com.example.service.impl;

import com.example.dto.admin.AdminTopicRes;
import com.example.dto.user.UserTopicRes;
import com.example.entity.Topic;
import com.example.exception.CustomerException;
import com.example.mapper.TopicMapper;
import com.example.repository.TopicRepository;
import com.example.service.TopicService;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * @author BAO 7/7/2023
 */
@Service
public class TopicServiceImpl implements TopicService {

  private final TopicRepository topicRepository;
  private final TopicMapper topicMapper;

  public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper) {
    this.topicRepository = topicRepository;
    this.topicMapper = topicMapper;
  }

  @Override
  public Topic save(Topic topic) {
    return topicRepository.save(topic);
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
    if (topicRepository.existsById(id)) throw new CustomerException("Something went wrong");
  }

  @Override
  public List<UserTopicRes> userFindByCourseId(int courseId) {
    List<Topic> topics = topicRepository.findByCourseId(courseId);
    return topicMapper.topicsToTopicsUserRes(topics);
  }
}
