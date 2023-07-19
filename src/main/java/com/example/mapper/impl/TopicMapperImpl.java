package com.example.mapper.impl;

import com.example.dto.user.UserTopicRes;
import com.example.entity.Topic;
import com.example.mapper.TopicMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        return topics.stream().map(this::topicToUserTopicRes).collect(Collectors.toList());
    }
}
