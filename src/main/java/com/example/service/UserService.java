package com.example.service;

import com.example.dto.user.UserCourseRes;
import com.example.dto.user.UserLearnRes;
import com.example.dto.user.UserTopicRes;
import com.example.entity.User;

import java.util.List;

/**
 * @author BAO 7/3/2023
 */
public interface UserService {

    List<User> getAllUser();
    User getUserById(Long id);



    List<UserLearnRes> getVocabulariesByTopicId(int topicId);
}
