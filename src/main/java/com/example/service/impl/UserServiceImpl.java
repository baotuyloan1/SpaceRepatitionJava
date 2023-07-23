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
  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;
  private final VocabularyService vocabularyService;
  private final AnswerMapper answerMapper;
  private final VocabularyMapper vocabularyMapper;
  private final TopicMapper topicMapper;

  public UserServiceImpl(
          UserRepository userRepository,
          TopicService topicService,
          CourseRepository courseRepository,
          CourseMapper courseMapper,
          VocabularyService vocabularyService,
          AnswerMapper answerMapper, VocabularyMapper vocabularyMapper, TopicMapper topicMapper) {
    this.userRepository = userRepository;
    this.topicService = topicService;
    this.courseRepository = courseRepository;
    this.courseMapper = courseMapper;
    this.vocabularyService = vocabularyService;
    this.answerMapper = answerMapper;
    this.vocabularyMapper = vocabularyMapper;
    this.topicMapper = topicMapper;
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
        .toList();
  }

  public void initLearningTypes(Set<TypeLearnRes> typeLearnResList, Vocabulary vocabulary) {
    typeLearnResList.add(new TypeLearnRes("info"));
    addSelectTypeToRes(typeLearnResList, vocabulary);
    typeLearnResList.add(new TypeLearnRes("mean"));
    typeLearnResList.add(new TypeLearnRes("listen"));
  }



  public void addSelectTypeToRes(Set<TypeLearnRes> typeLearnResList, Vocabulary vocabulary) {
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
