package com.example.mapper;

import com.example.dto.user.UserTopicRes;
import com.example.entity.Topic;

import java.util.List;

/**
 * @author BAO 7/19/2023
 */
public interface TopicMapper {

    UserTopicRes topicToUserTopicRes(Topic topic);
    List<UserTopicRes> topicsToTopicsUserResponse(List<Topic> topics);
}
