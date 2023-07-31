package com.example.service.impl;

import com.example.dto.user.learn.UserLearnRes;
import com.example.entity.*;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.AnswerMapper;
import com.example.mapper.VocabularyMapper;
import com.example.repository.UserRepository;
import com.example.security.services.UserDetailsImpl;
import com.example.service.UserService;
import com.example.service.UserVocabularyService;
import com.example.service.VocabularyService;

import java.util.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author BAO 7/3/2023
 */
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final VocabularyService vocabularyService;
  private final AnswerMapper answerMapper;
  private final VocabularyMapper vocabularyMapper;
  private final UserVocabularyService userVocabularyService;

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


//  @Transactional
//  @Override
//  public List<UserLearnRes> getVocabulariesByTopicId(int topicId) {
//    UserDetailsImpl userDetails =
//        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    List<Vocabulary> notLearnedWords =
//        vocabularyService.findWordsNotLearnedByUserIdInTopicId(userDetails.getId(), topicId);
//    return convertVocabulariesToUserRes(notLearnedWords);
//  }

  @Override
  public List<UserLearnRes> getNextWordToReview() {
    Date currentDate = new Date();
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//    Date nearestDate = userVocabularyService.getNearestDate(userDetails.getId());
    /** Lấy các ngày nhỏ nhất trong database */
    return null;
  }

//  private List<UserLearnRes> convertVocabulariesToUserRes(List<Vocabulary> notLearnedWords) {
//    return notLearnedWords.stream()
//        .map(
//            vocabulary -> {
//              Set<TypeLearnRes> typeLearnResList = new LinkedHashSet<>();
//              initLearningTypes(typeLearnResList, vocabulary);
//              UserLearnRes userLearnRes = vocabularyMapper.vocabularyToUserLearnRes(vocabulary);
//              userLearnRes.setLearnTypes(typeLearnResList);
//              return userLearnRes;
//            })
//        .toList();
//  }
//
//  public void initLearningTypes(Set<TypeLearnRes> typeLearnResList, Vocabulary vocabulary) {
//    typeLearnResList.add(new TypeLearnRes("info"));
//    addSelectTypeToRes(typeLearnResList, vocabulary);
//    typeLearnResList.add(new TypeLearnRes("mean"));
//    typeLearnResList.add(new TypeLearnRes("listen"));
//  }
//
//  public void addSelectTypeToRes(Set<TypeLearnRes> typeLearnResList, Vocabulary vocabulary) {
//    List<Question> questionList = vocabulary.getQuestion();
//    if (!questionList.isEmpty()) {
//      Question question = getQuestionRandom(questionList);
//      TypeQuestionRes typeQuestionRes = new TypeQuestionRes();
//      typeQuestionRes.setIdQuestion(question.getId());
//      typeQuestionRes.setQuestion(question.getQuestion());
//      typeQuestionRes.setType("select");
//      typeQuestionRes.setAnswers(answerMapper.answersToUserAnswersRes(question.getAnswers()));
//      typeQuestionRes.setIdRightAnswer(question.getAnswer().getId());
//      typeLearnResList.add(typeQuestionRes);
//    }
//  }
//
//  private static Question getQuestionRandom(List<Question> questions) {
//    int indexQuestion = (int) Math.floor(Math.random() * questions.size());
//    return questions.get(indexQuestion);
//  }

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
