package com.example.service;

import com.example.dto.TopicResponse;
import com.example.entity.Topic;

import java.util.List;

/**
 * @author BAO 7/7/2023
 */
public interface TopicService {
    Topic save(Topic topic);

    List<Topic> findByCourseId(int courseId);

    List<TopicResponse> listAll();

    void deleteTopicById(int id);
}
