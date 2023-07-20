package com.example.service.impl;

import com.example.dto.user.*;
import com.example.entity.*;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.AnswerMapper;
import com.example.mapper.CourseMapper;
import com.example.mapper.TopicMapper;
import com.example.mapper.VocabularyMapper;
import com.example.repository.CourseRepository;
import com.example.repository.UserRepository;
import com.example.security.services.UserDetailsImpl;
import com.example.service.QuestionService;
import com.example.service.TopicService;
import com.example.service.UserService;
import com.example.service.VocabularyService;
import jakarta.transaction.Transactional;
import java.util.*;
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
  private final QuestionService questionService;
  private final AnswerMapper answerMapper;
  private final VocabularyMapper vocabularyMapper;

  public UserServiceImpl(
          UserRepository userRepository,
          TopicService topicService,
          TopicMapper topicMapper,
          CourseRepository courseRepository,
          CourseMapper courseMapper,
          VocabularyService vocabularyService,
          QuestionService questionService,
          AnswerMapper answerMapper, VocabularyMapper vocabularyMapper) {
    this.userRepository = userRepository;
    this.topicService = topicService;
    this.topicMapper = topicMapper;
    this.courseRepository = courseRepository;
    this.courseMapper = courseMapper;
    this.vocabularyService = vocabularyService;
    this.questionService = questionService;
    this.answerMapper = answerMapper;
    this.vocabularyMapper = vocabularyMapper;
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

  @Transactional
  @Override
  public List<UserLearnRes> getVocabulariesByTopicId(int topicId) {
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<Vocabulary> notLearnedWords =
        vocabularyService.findWordsNotLearnedByUserIdInTopicId(userDetails.getId(), topicId);
    return convertVocabulariesToUserRes(notLearnedWords);
  }

  private List<UserLearnRes> convertVocabulariesToUserRes(List<Vocabulary> notLearnedWords) {
    return notLearnedWords.stream()
        .map(
            vocabulary -> {
              Set<TypeLearnRes> typeLearnResList = new LinkedHashSet<>();
              initLearningTypes(typeLearnResList, vocabulary);
              UserLearnRes userLearnRes = vocabularyMapper.vocabularyToUserLearnRes(vocabulary);
              userLearnRes.setLearnTypes(typeLearnResList);
              return userLearnRes;
            })
        .collect(Collectors.toList());
  }

  public void initLearningTypes(Set<TypeLearnRes> typeLearnResList, Vocabulary vocabulary) {
    typeLearnResList.add(new TypeLearnRes("info"));
    addselectTypeToRes(typeLearnResList, vocabulary);
    typeLearnResList.add(new TypeLearnRes("mean"));
    typeLearnResList.add(new TypeLearnRes("listen"));
  }



  public void addselectTypeToRes(Set<TypeLearnRes> typeLearnResList, Vocabulary vocabulary) {
    List<Question> questionList = vocabulary.getQuestion();
    if (!questionList.isEmpty()) {
      int indexQuestion = (int) Math.floor(Math.random() * questionList.size());
      Question question = questionList.get(indexQuestion);
      TypeQuestionRes typeQuestionRes = new TypeQuestionRes();
      typeQuestionRes.setIdQuestion(question.getId());
      typeQuestionRes.setQuestion(question.getQuestion());
      typeQuestionRes.setType("select");
      typeQuestionRes.setAnswers(answerMapper.answersToUserAnswersRes(question.getAnswers()));
      typeQuestionRes.setIdRightAnswer(question.getAnswer().getId());
      typeLearnResList.add(typeQuestionRes);
    }
  }

  private boolean isLearnedAll(Topic topic) {
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<Vocabulary> vocabulariesInTopic = topic.getVocabulary();
    List<Vocabulary> learnedVocabularies =
        vocabularyService.getLearnedWordInTopicByUserId(topic, userDetails.getId());
    /** convert thành set để tăng performance, list phải duyệt qua từng phần tử */
    return new HashSet<>(learnedVocabularies).containsAll(vocabulariesInTopic)
        && !vocabulariesInTopic.isEmpty();
  }
}
