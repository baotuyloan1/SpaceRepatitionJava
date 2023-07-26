package com.example.service;

import com.example.dto.user.UserLearnRes;
import com.example.dto.user.UserVocabularyRequest;
import com.example.dto.user.learn.UserSelectReq;
import com.example.dto.user.learn.UserSelectRes;
import com.example.entity.UserVocabulary;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author BAO 7/13/2023
 */
@Service
public interface UserVocabularyService {

  UserVocabulary saveLearnNewWord(long id, boolean isLearnAgain);

  UserVocabulary updateLearnedVocabulary(UserVocabularyRequest userVocabularyRequest);

  List<UserLearnRes> getNextWordToReview();

  List<UserLearnRes> getVocabulariesByTopicId(int topicId);

  UserSelectRes saveNewVocabulary(UserSelectReq req);

  UserSelectRes updateReviewVocabulary(UserSelectReq req);
}
