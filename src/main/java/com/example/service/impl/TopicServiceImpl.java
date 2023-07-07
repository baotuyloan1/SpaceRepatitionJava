package com.example.service.impl;

import com.example.dto.TopicResponse;
import com.example.entity.Topic;
import com.example.exception.CustomerException;
import com.example.repository.TopicRepository;
import com.example.service.TopicService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author BAO 7/7/2023
 */
@Service
public class TopicServiceImpl implements TopicService {

  private final TopicRepository topicRepository;

  public TopicServiceImpl(TopicRepository topicRepository) {
    this.topicRepository = topicRepository;
  }

  @Override
  public Topic save(Topic topic) {
    return topicRepository.save(topic);
  }

  @Override
  public List<Topic> findByCourseId(int courseId) {
    return topicRepository.findByCourseId(courseId);
  }

  @Override
  public List<TopicResponse> listAll() {
    return listTopicToResponse(topicRepository.findAll());
  }

  @Override
  public void deleteTopicById(int id) {
      topicRepository.deleteById(id);
      if (topicRepository.existsById(id)) throw new CustomerException("Something went wrong");
  }

  TopicResponse topicToResponse(Topic topic) {
    TopicResponse topicResponse = new TopicResponse();
    topicResponse.setCourse(topic.getCourse());
    topicResponse.setTitleVn(topic.getTitleVn());
    topicResponse.setTitleEn(topic.getTitleEn());
    topicResponse.setId(topic.getId());
    topicResponse.setVocabulary(topic.getVocabulary());
    return topicResponse;
  }

  List<TopicResponse> listTopicToResponse(List<Topic> topicList) {
    return topicList.stream().map(this::topicToResponse).collect(Collectors.toList());
  }
}
