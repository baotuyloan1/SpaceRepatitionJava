package com.example.service.impl;

import com.example.dto.user.UserVocabularyRequest;
import com.example.entity.*;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AnswerRepository;
import com.example.repository.UserRepository;
import com.example.repository.UserVocabularyRepository;
import com.example.repository.VocabularyRepository;
import com.example.security.services.UserDetailsImpl;
import com.example.service.UserVocabularyService;
import java.util.*;
import java.util.concurrent.TimeUnit;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author BAO 7/13/2023
 */
@Service
public class UserVocabularyServiceImpl implements UserVocabularyService {

  private final UserVocabularyRepository userVocabularyRepository;

  private final AnswerRepository answerRepository;

  private final UserRepository userRepository;

  private final VocabularyRepository vocabularyRepository;

  @Value("${learnEnglish.app.defaultFE}")
  private float defaultFE;

  @Value("${learnEnglish.app.timeRepatitionDF}")
  private int defaultTimeRepatition;

  public UserVocabularyServiceImpl(
      UserVocabularyRepository userVocabularyRepository,
      AnswerRepository answerRepository,
      UserRepository userRepository,
      VocabularyRepository vocabularyRepository) {
    this.userVocabularyRepository = userVocabularyRepository;
    this.answerRepository = answerRepository;
    this.userRepository = userRepository;
    this.vocabularyRepository = vocabularyRepository;
  }

  private UserVocabulary saveLearnNewWord(long vocabularyId) {
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    UserVocabulary userVocabulary = new UserVocabulary();
    userVocabulary.setId(new UserVocabularyId(userDetails.getId(), vocabularyId));
    long timeRepetition = Math.round(defaultTimeRepatition * defaultFE);
    Date currentDate = new Date();
    long endDateMili =
        currentDate.getTime() + TimeUnit.DAYS.toMillis(Math.round(defaultTimeRepatition));
    Date endDate = new Date(endDateMili);
    userVocabulary.setEndDate(endDate);
    userVocabulary.setSubmitDate(new Date());
    userVocabulary.setTimeRepetition(timeRepetition);
    return userVocabularyRepository.save(userVocabulary);
  }

  UserVocabulary updateUserVocabulary(long id) {
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    UserVocabulary userVocabulary =
        userVocabularyRepository
            .findById(new UserVocabularyId(userDetails.getId(), id))
            .orElseThrow(() -> new ResourceNotFoundException(false, "User vocabulary not found"));

    float timeRepatition =
        (userVocabulary.getEndDate().getTime() - userVocabulary.getSubmitDate().getTime())
            * defaultFE;
    Date currentDate = new Date();
    long endDateMili = currentDate.getTime() + Math.round(timeRepatition);
    Date endDate = new Date(endDateMili);
    userVocabulary.setSubmitDate(currentDate);
    userVocabulary.setEndDate(endDate);
    return userVocabularyRepository.save(userVocabulary);
  }

  @Override
  public UserVocabulary saveNewLearnedVocabulary(long id) {
    return saveLearnNewWord(id);
  }

  @Override
  public UserVocabulary updateLearnedVocabulary(UserVocabularyRequest userVocabularyRequest) {
    if (userVocabularyRequest.isRightAnswer() ) {
      return updateUserVocabulary(userVocabularyRequest.getIdVocabulary());
    } else return saveNewLearnedVocabulary(userVocabularyRequest.getIdVocabulary());
  }

  @Transactional
  @Override
  public List<Map<String, Object>> getNextWordToReview() {
    Date currentDate = new Date();
    List<UserVocabulary> userVocabularyList = new LinkedList<>();
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    /**
     * + lấy toàn bộ các từ vựng được có end date sau hiện tại hoặc toàn bộ các tự vựng có thời gian
     * nhỏ nhất + 2 giờ
     */

    /** Lấy các ngày nhỏ nhất trong database */
    Date nearestDate = userVocabularyRepository.getNearestDate(userDetails.getId());
    /*
    Ngày ôn tập trước ngày hiện tại
     */
    if (currentDate.compareTo(nearestDate) >= 1) {
      userVocabularyList =
          userVocabularyRepository.getVocabularyBeforeCurrent(userDetails.getId(), new Date());
    } else {
      /** Lấy các từ vựng trước thời gian gần nhất + 1 tiếng để tính số từ vựng cần ôn tập */
      userVocabularyList =
          userVocabularyRepository.getVocabularyBeforeCurrent(
              userDetails.getId(), new Date(nearestDate.getTime() + 60 * 60 * 1000));
    }

    List<Map<String, Object>> objectWords = new LinkedList<>();

    for (UserVocabulary userVocabulary : userVocabularyList) {

      Map<String, Object> word = new LinkedHashMap<>();
      word.put("id", userVocabulary.getId());
      word.put("word", userVocabulary.getVocabulary());
      word.put("endDate",userVocabulary.getEndDate());
      List<Object> learningTypes = new LinkedList<>();

      Map<String, Object> map = new LinkedHashMap<>();

      int randomTypeQuestion = (int) Math.floor(Math.random() * 2);

      if (randomTypeQuestion == 0) {
        /** Nghe phát âm từ và điền lại từ cho đúng */
        Map<String, Object> listenMap = new LinkedHashMap<>();
        listenMap.put("id", 2);
        listenMap.put("type", "listen");
        learningTypes.add(listenMap);
      } else {
        /** Hiển thị nghĩa của từ và ghi lại từ */
        Map<String, Object> meanMap = new LinkedHashMap<>();
        meanMap.put("id", 3);
        meanMap.put("type", "mean");
        learningTypes.add(meanMap);
      }

      /** Random các loại bài tập. Chỉ lấy 1 loại bài tập của mỗi từ */
      List<Question> questionList = userVocabulary.getVocabulary().getQuestion();
      int sizeList = questionList.size();
      if (sizeList > 0) {
        int indexQuestion = (int) Math.floor(Math.random() * sizeList);
        Question currentQuestion = questionList.get(indexQuestion);
        map.put("id", 1);
        map.put("type", "select");
        map.put("question", currentQuestion);

        /** Selection type */
        // dùng foreach lấy từ bảng ra các đáp án của question id
        List<Answer> listAnswer = answerRepository.findByQuestionId(currentQuestion.getId());
        map.put("answers", listAnswer);
        map.put("rightQuestionId", currentQuestion.getAnswer().getId());
        learningTypes.add(map);
      }
      word.put("learningTypes", learningTypes);

      objectWords.add(word);
    }

    return objectWords;
  }
}
