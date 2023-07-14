package com.example.service.impl;

import com.example.dto.user.NextTimeReviewResonse;
import com.example.dto.user.UserVocabularyRequest;
import com.example.entity.UserVocabulary;
import com.example.entity.UserVocabularyId;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserRepository;
import com.example.repository.UserVocabularyRepository;
import com.example.repository.VocabularyRepository;
import com.example.security.services.UserDetailsImpl;
import com.example.service.UserVocabularyService;
import java.util.Date;
import java.util.List;
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

  private final UserRepository userRepository;

  private final VocabularyRepository vocabularyRepository;

  @Value("${learnEnglish.app.defaultFE}")
  private float defaultFE;

  @Value("${learnEnglish.app.timeRepatitionDF}")
  private int defaultTimeRepatition;

  public UserVocabularyServiceImpl(
      UserVocabularyRepository userVocabularyRepository,
      UserRepository userRepository,
      VocabularyRepository vocabularyRepository) {
    this.userVocabularyRepository = userVocabularyRepository;
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
    if (userVocabularyRequest.getIsRightAnswer().equals("true")) {
      return updateUserVocabulary(userVocabularyRequest.getVocabularyId());
    } else return saveNewLearnedVocabulary(userVocabularyRequest.getVocabularyId());
  }

  @Override
  public List<UserVocabulary> getNextTimeToReview() {
    Date currentDate = new Date();
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    /** + lấy toàn bộ các từ vựng được có end date sau hiện tại hoặc toàn bộ các tự vựng có thời gian nhỏ nhất + 2 giờ*/

    /**
     * Lấy các ngày nhỏ nhất trong database
     */
    Date nearestDate = userVocabularyRepository.getNearestDate(userDetails.getId()
    );
    /*
    Ngày ôn tập trước ngày hiện tại
     */
    if (currentDate.compareTo(nearestDate) >= 1){
        return userVocabularyRepository.getVocabularyBeforeCurrent(userDetails.getId(), new Date());
    }else {
      /**
       * Lấy các từ vựng trước thời gian gần nhất + 1 tiếng để tính số từ vựng cần ôn tập
       */
      return userVocabularyRepository.getVocabularyBeforeCurrent(userDetails.getId(), new Date(nearestDate.getTime() + 60*60*1000));
    }
  }
}
