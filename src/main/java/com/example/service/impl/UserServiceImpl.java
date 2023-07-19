package com.example.service.impl;

import com.example.dto.user.UserCourseResponse;
import com.example.dto.user.UserTopicRes;
import com.example.entity.*;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.CourseMapper;
import com.example.mapper.TopicMapper;
import com.example.repository.CourseRepository;
import com.example.repository.UserRepository;
import com.example.security.services.UserDetailsImpl;
import com.example.service.TopicService;
import com.example.service.UserService;
import com.example.service.VocabularyService;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author BAO 7/3/2023
 */
@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final TopicService topicService;
  private final TopicMapper topicMapper;
  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;
  private final VocabularyService vocabularyService;

  public UserServiceImpl(
      UserRepository userRepository,
      TopicService topicService,
      TopicMapper topicMapper,
      CourseRepository courseRepository,
      CourseMapper courseMapper,
      VocabularyService vocabularyService) {
    this.userRepository = userRepository;
    this.topicService = topicService;
    this.topicMapper = topicMapper;
    this.courseRepository = courseRepository;
    this.courseMapper = courseMapper;
    this.vocabularyService = vocabularyService;
  }

  @Override
  public List<User> getAllUser() {
    return userRepository.findAll();
  }

  @Override
  public User getUserById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(false, "User not found"));
  }

  @Override
  public List<UserCourseResponse> getListCourses() {
    List<Course> courseList = courseRepository.findAll();
    return courseMapper.coursesToCoursesUserResponse(courseList);
  }

  @Transactional
  @Override
  public List<UserTopicRes> findTopicByCourseId(int courseId) {
    List<Topic> topics = topicService.findByCourseId(courseId);

    return topics.stream()
        .map(
            topic ->
                new UserTopicRes(
                    topic.getId(), topic.getTitleEn(), topic.getTitleVn(), isLearnedAll(topic)))
        .collect(Collectors.toList());
  }

  private boolean isLearnedAll(Topic topic) {
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<Vocabulary> vocabularies = topic.getVocabulary();
    /** Lấy ra từ đã học của người dùng trong topic này */
    List<Vocabulary> learnedVocabularies =
        vocabularyService.getLearnedWordByTopicAndUserId(topic, userDetails.getId());
    /** convert thành set để tăng performance, list phải duyệt qua từng phần tử */
    return new HashSet<>(learnedVocabularies).containsAll(vocabularies) && !vocabularies.isEmpty();
  }
}
