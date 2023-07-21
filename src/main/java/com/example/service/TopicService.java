package com.example.service;

import com.example.dto.admin.AdminTopicRes;
import com.example.entity.Topic;

import java.util.List;

/**
 * @author BAO 7/7/2023
 */
public interface TopicService {
    Topic save(Topic topic);

    List<AdminTopicRes> findByCourseId(int courseId);

    List<AdminTopicRes> listAll();

    void deleteTopicById(int id);
}
