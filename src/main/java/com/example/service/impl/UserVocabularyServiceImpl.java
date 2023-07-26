package com.example.service.impl;

import static java.lang.Math.round;

import com.example.config.PropertiesConfig;
import com.example.dto.user.TypeLearnRes;
import com.example.dto.user.TypeQuestionRes;
import com.example.dto.user.UserLearnRes;
import com.example.dto.user.UserVocabularyRequest;
import com.example.dto.user.learn.UserSelectReq;
import com.example.dto.user.learn.UserSelectRes;
import com.example.entity.*;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.AnswerMapper;
import com.example.mapper.VocabularyMapper;
import com.example.repository.QuestionRepository;
import com.example.repository.UserVocabularyRepository;
import com.example.security.services.UserDetailsImpl;
import com.example.service.UserVocabularyService;
import com.example.service.VocabularyService;
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author BAO 7/13/2023
 */
@Service
@AllArgsConstructor
public class UserVocabularyServiceImpl implements UserVocabularyService {
  private final UserVocabularyRepository userVocabularyRepository;
  private final VocabularyService vocabularyService;
  private final PropertiesConfig env;
  private final VocabularyMapper vocabularyMapper;
  private final AnswerMapper answerMapper;
  private final Random random;
  private final QuestionRepository questionRepository;

  public UserVocabulary saveLearnNewWord(long vocabularyId, boolean isLearnAgain, float ef) {
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserVocabulary userVocabulary = new UserVocabulary();
    userVocabulary.setId(new UserVocabularyId(userDetails.getId(), vocabularyId));
    Date currentDate = new Date();
    userVocabulary.setSubmitDate(currentDate);
    userVocabulary.setEf(ef);
    if (isLearnAgain) {
      short q = (short) (env.getDefaultQ() - (short) 1);
      userVocabulary.setQ(q);
      userVocabulary.setReviewDate(currentDate);
      userVocabulary.setCountLearn(0);
    } else {
      userVocabulary.setCountLearn(1);
      userVocabulary.setQ(env.getDefaultQ());
      userVocabulary.setReviewDate(calculateDateDayToReview(currentDate, env.getDefaultFirstDay()));
    }
    return userVocabularyRepository.save(userVocabulary);
  }

  private Date calculateDateDayToReview(Date currentDate, int dayInterval) {
    long endDateMil = currentDate.getTime() + TimeUnit.DAYS.toMillis(dayInterval);
    return new Date(endDateMil);
  }

  private UserVocabulary updateUserVocabulary(long id) {
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    UserVocabulary userVocabulary =
        userVocabularyRepository
            .findById(new UserVocabularyId(userDetails.getId(), id))
            .orElseThrow(() -> new ResourceNotFoundException(false, "User vocabulary not found"));
    float timeRepatition =
        (userVocabulary.getReviewDate().getTime() - userVocabulary.getSubmitDate().getTime())
            * env.getDefaultEF();
    Date currentDate = new Date();
    long endDateMil = currentDate.getTime() + round(timeRepatition);
    Date endDate = new Date(endDateMil);
    userVocabulary.setSubmitDate(currentDate);
    userVocabulary.setReviewDate(endDate);
    return userVocabularyRepository.save(userVocabulary);
  }

  @Override
  public UserVocabulary updateLearnedVocabulary(UserVocabularyRequest userVocabularyRequest) {
    if (userVocabularyRequest.isRightAnswer()) {
      return updateUserVocabulary(userVocabularyRequest.getIdVocabulary());
    } else return saveLearnNewWord(userVocabularyRequest.getIdVocabulary(), true);
  }

  private Date getNearestDate(long userId) {
    return userVocabularyRepository.getNearestDate(userId);
  }

  @Transactional
  @Override
  public List<UserLearnRes> getNextWordToReview() {
    Date currentDate = new Date();
    List<UserVocabulary> userVocabularyList;
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    /**
     * + lấy toàn bộ các từ vựng được có end date sau hiện tại hoặc toàn bộ các tự vựng có thời gian
     * nhỏ nhất + 2 giờ
     */
    /** Lấy các ngày nhỏ nhất trong database */
    Date nearestDate = getNearestDate(userDetails.getId());
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

    boolean isNew = false;
    List<Vocabulary> vocabularies = getVocabulariesFromUserVocabularies(userVocabularyList);
    return convertReviewVocabulariesToUserRes(vocabularies);

    //    List<Map<String, Object>> objectWords = new LinkedList<>();
    //    for (UserVocabulary userVocabulary : userVocabularyList) {
    //      Map<String, Object> word = new LinkedHashMap<>();
    //      word.put("id", userVocabulary.getId());
    //      word.put("word", userVocabulary.getVocabulary());
    //      word.put("endDate", userVocabulary.getEndDate());
    //      List<Object> learningTypes = new LinkedList<>();
    //      Map<String, Object> map = new LinkedHashMap<>();
    //      int randomTypeQuestion = (int) Math.floor(Math.random() * 2);
    //      if (randomTypeQuestion == 0) {
    //        /** Nghe phát âm từ và điền lại từ cho đúng */
    //        Map<String, Object> listenMap = new LinkedHashMap<>();
    //        listenMap.put("id", 2);
    //        listenMap.put("type", "listen");
    //        learningTypes.add(listenMap);
    //      } else {
    //        /** Hiển thị nghĩa của từ và ghi lại từ */
    //        Map<String, Object> meanMap = new LinkedHashMap<>();
    //        meanMap.put("id", 3);
    //        meanMap.put("type", "mean");
    //        learningTypes.add(meanMap);
    //      }
    //      /** Random các loại bài tập. Chỉ lấy 1 loại bài tập của mỗi từ */
    //      List<Question> questionList = userVocabulary.getVocabulary().getQuestion();
    //      int sizeList = questionList.size();
    //      if (sizeList > 0) {
    //        int indexQuestion = (int) Math.floor(Math.random() * sizeList);
    //        Question currentQuestion = questionList.get(indexQuestion);
    //        map.put("id", 1);
    //        map.put("type", "select");
    //        map.put("question", currentQuestion);
    //
    //        /** Selection type */
    //        // dùng foreach lấy từ bảng ra các đáp án của question id
    //        List<Answer> listAnswer = answerRepository.findByQuestionId(currentQuestion.getId());
    //        map.put("answers", listAnswer);
    //        map.put("rightQuestionId", currentQuestion.getAnswer().getId());
    //        learningTypes.add(map);
    //      }
    //      word.put("learningTypes", learningTypes);
    //      objectWords.add(word);
    //    }
    //    return objectWords;
  }

  private List<Vocabulary> getVocabulariesFromUserVocabularies(
      List<UserVocabulary> userVocabularyList) {
    return userVocabularyList.stream().map(UserVocabulary::getVocabulary).toList();
  }

  @Transactional
  @Override
  public List<UserLearnRes> getVocabulariesByTopicId(int topicId) {
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<Vocabulary> notLearnedWords =
        vocabularyService.findWordsNotLearnedByUserIdInTopicId(userDetails.getId(), topicId);
    return convertNewVocabulariesToUserRes(notLearnedWords);
  }

  /**
   * @param req
   * @return
   */
  @Transactional
  @Override
  public UserSelectRes saveNewVocabulary(UserSelectReq req) {
    UserSelectRes res = new UserSelectRes();
    Question question =
        questionRepository
            .findById(req.getIdQuestion())
            .orElseThrow(() -> new ResourceNotFoundException(false, "Question can't be find"));
    boolean isLearnAgain = !isRightAnswer(req, question);
    float ef = env.getDefaultEF();
    saveLearnNewWord(question.getVocabulary().getId(), isLearnAgain, ef);
    res.setLearnAgain(isLearnAgain);
    return res;
  }

  @Override
  public UserSelectRes updateReviewVocabulary(UserSelectReq req) {
    UserSelectRes res = new UserSelectRes();
    Question question =
        questionRepository
            .findById(req.getIdQuestion())
            .orElseThrow(() -> new ResourceNotFoundException(false, "Question can't be find"));
    boolean isLearnAgain = !isRightAnswer(req, question);
    updateReviewVocabulary(question.getVocabulary().getId(), isLearnAgain);
    res.setLearnAgain(isLearnAgain);
    return res;
  }

  private void updateReviewVocabulary(long vocabularyId, boolean isLearnAgain) {
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserVocabularyId id = new UserVocabularyId(userDetails.getId(), vocabularyId);
    UserVocabulary userVocabulary =
        userVocabularyRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(false, " User Vocabulary not found"));
    Date currentDate = new Date();

    if (isLearnAgain) {
      updateUserVocabularyWithFalseAnswer(vocabularyId, userVocabulary, currentDate);
    } else {
      updateUserVocabularyWithRightAnswer(userVocabulary, currentDate);
    }

    return userVocabularyRepository.save(userVocabulary);
  }

  private void updateUserVocabularyWithFalseAnswer(
      long vocabularyId, UserVocabulary userVocabulary, Date currentDate) {
    /** wrong q= q-1; true q=q+1; */
    short q = (short) (userVocabulary.getQ() - 1);
    userVocabulary.setQ(q);
    if (userVocabulary.getCountLearn() > 2) {
      updateUserVocabularyFalseWithCountLearnMoreThan2(vocabularyId, userVocabulary, q);
    } else {
      updateUserVocabularyFalseWithCountLearnLessThan2(userVocabulary, currentDate, q);
    }
  }

  private void updateUserVocabularyFalseWithCountLearnLessThan2(
      UserVocabulary userVocabulary, Date currentDate, short q) {
    userVocabulary.setQ(q);
    userVocabulary.setSubmitDate(currentDate);
    userVocabulary.setReviewDate(currentDate);
  }

  private void updateUserVocabularyFalseWithCountLearnMoreThan2(
      long vocabularyId, UserVocabulary userVocabulary, short q) {
    float currentEF = calculateCurrentEF(userVocabulary.getEf(), q);
    float leastEF = env.getLeastEF();
    if (currentEF < leastEF) {
      currentEF = leastEF;
      userVocabulary.setEf(currentEF);
    }
    if (q < 3) {
      saveLearnNewWord(vocabularyId, true, currentEF);
    } else {
      updateReviewWord(userVocabulary, true, currentEF);
    }
  }

  private void updateUserVocabularyWithRightAnswer(UserVocabulary userVocabulary, Date currentDate) {
    userVocabulary.setCountLearn(userVocabulary.getCountLearn() + 1);
    short q = userVocabulary.getQ();
    if (q < 5) {
      userVocabulary.setQ(++q);
    } else {
      userVocabulary.setQ(q);
    }
    if (userVocabulary.getCountLearn() < 2){
      userVocabulary.setReviewDate(calculateDateDayToReview(currentDate, env.getDefaultSecondDay()));
    }else{
      userVocabulary.setReviewDate(calculateDateDayToReview(currentDate, ))
    }
    float currentEF = calculateCurrentEF(userVocabulary.getEf(), q);
    updateReviewWord(userVocabulary, false, currentEF);
  }

  private void updateReviewWord(
      UserVocabulary userVocabulary, boolean isLearnAgain, float currentEF) {

  }

  public float calculateCurrentEF(float currentEF, short q) {
    return (float) (currentEF + (0.1 - (5 - q) * (0.08 + (5 - q) * 0.02)));
  }

  private static boolean isRightAnswer(UserSelectReq req, Question question) {
    return question.getRightAnswer().getId() == req.getIdAnswer();
  }

  private List<UserLearnRes> convertNewVocabulariesToUserRes(List<Vocabulary> vocabularies) {
    return vocabularies.stream()
        .map(
            vocabulary -> {
              Set<TypeLearnRes> typeLearnResList = new LinkedHashSet<>();
              addInfoType(typeLearnResList);
              addSelectType(typeLearnResList, vocabulary);
              addMeaningType(typeLearnResList);
              addListeningType(typeLearnResList);
              UserLearnRes userLearnRes = vocabularyMapper.vocabularyToUserLearnRes(vocabulary);
              userLearnRes.setLearnTypes(typeLearnResList);
              return userLearnRes;
            })
        .toList();
  }

  private void addListeningType(Set<TypeLearnRes> typeLearnResList) {
    typeLearnResList.add(new TypeLearnRes("listen"));
  }

  private void addMeaningType(Set<TypeLearnRes> typeLearnResList) {
    typeLearnResList.add(new TypeLearnRes("mean"));
  }

  private void addInfoType(Set<TypeLearnRes> typeLearnResList) {
    typeLearnResList.add(new TypeLearnRes("info"));
  }

  private void addSelectType(Set<TypeLearnRes> typeLearnResList, Vocabulary vocabulary) {
    List<Question> questionList = vocabulary.getQuestion();
    if (!questionList.isEmpty()) {
      Question question = getQuestionRandom(questionList);
      TypeQuestionRes typeQuestionRes = new TypeQuestionRes();
      typeQuestionRes.setIdQuestion(question.getId());
      typeQuestionRes.setQuestion(question.getQuestion());
      typeQuestionRes.setType("select");
      typeQuestionRes.setAnswers(answerMapper.answersToUserAnswersRes(question.getAnswers()));
      typeQuestionRes.setIdRightAnswer(question.getRightAnswer().getId());
      typeLearnResList.add(typeQuestionRes);
    }
  }

  private List<UserLearnRes> convertReviewVocabulariesToUserRes(List<Vocabulary> vocabularies) {
    return vocabularies.stream()
        .map(
            vocabulary -> {
              Set<TypeLearnRes> typeLearnResList = new LinkedHashSet<>();
              addRandomLearningTypes(typeLearnResList, vocabulary);
              UserLearnRes userLearnRes = vocabularyMapper.vocabularyToUserLearnRes(vocabulary);
              userLearnRes.setLearnTypes(typeLearnResList);
              return userLearnRes;
            })
        .toList();
  }

  private void addRandomLearningTypes(Set<TypeLearnRes> typeLearnResList, Vocabulary vocabulary) {
    /**
     * Ôn tập
     *
     * <p>20% từ đó là trac nghiem 40% tu do la nghe, 40% dien` tu 1-2 trac nghiem, 3-6 nghe, 7-10
     * dien` tu`
     */
    int randomTypeQuestion = random.nextInt(12);
    if (randomTypeQuestion <= 2) {
      addSelectType(typeLearnResList, vocabulary);
    } else if (randomTypeQuestion <= 6) {
      addListeningType(typeLearnResList);
    } else {
      addMeaningType(typeLearnResList);
    }
  }

  private static Question getQuestionRandom(List<Question> questions) {
    int indexQuestion = (int) Math.floor(Math.random() * questions.size());
    return questions.get(indexQuestion);
  }
}
