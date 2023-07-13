package com.example.service.impl;

import com.example.entity.User;
import com.example.entity.UserVocabulary;
import com.example.entity.Vocabulary;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserRepository;
import com.example.repository.UserVocabularyRepository;
import com.example.repository.VocabularyRepository;
import com.example.security.services.UserDetailsImpl;
import com.example.service.UserVocabularyService;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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
    User user =
        userRepository
            .findById(userDetails.getId())
            .orElseThrow(() -> new ResourceNotFoundException(false, "Can find user"));
    Vocabulary vocabulary =
        vocabularyRepository
            .findById(vocabularyId)
            .orElseThrow(() -> new ResourceNotFoundException(false, "Vocabulary not found"));
    UserVocabulary userVocabulary = new UserVocabulary();
    long timeRepetition = Math.round(defaultTimeRepatition * defaultFE);
    userVocabulary.setCountFalse((short) 0);
    userVocabulary.setVocabulary(vocabulary);
    userVocabulary.setUser(user);
    userVocabulary.setCurrentFE(defaultFE);
    userVocabulary.setEndDate(
        new Date(new Date().getTime() + TimeUnit.MINUTES.toMillis(timeRepetition)));
    userVocabulary.setSubmitDate(new Date());
    userVocabulary.setTimeRepetition(timeRepetition);
    return userVocabularyRepository.save(userVocabulary);
  }

  @Override
  public UserVocabulary saveNewLearnedVocabulary(long id) {
    saveLearnNewWord(id);

    return saveLearnNewWord(id);
  }
}
