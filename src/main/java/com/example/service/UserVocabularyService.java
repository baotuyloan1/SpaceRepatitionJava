package com.example.service;

import com.example.dto.fcm.PushNotificationRequest;
import com.example.dto.BaseResApi;
import com.example.dto.user.UserVocabularyRequest;
import com.example.dto.user.learn.*;
import com.example.dto.user.learn.UserLearnRes;
import com.example.dto.user.review.UserNextWordsReq;
import com.example.dto.user.review.UserReviewReq;
import com.example.dto.user.review.UserReviewRes;
import com.example.entity.UserVocabularyId;
import com.example.payload.response.UserInfoResponse;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author BAO 7/13/2023
 */
@Service
public interface UserVocabularyService {

  List<UserNextWordsReq> getWordToReview();

  List<UserLearnRes> getVocabulariesByTopicId(int topicId);

  UserVocabularyId createUserVocabulary(UserLearnNewReq req);

  UserReviewRes updateReviewSelect(UserReviewSelectionReq req);

  UserReviewRes updateReview(UserReviewReq req);

  UserInfoResponse getInfo();

  void saveDeviceToken(PushNotificationRequest req);

  void updateLearnedVocabulary(UserVocabularyRequest userVocabularyRequest);

  BaseResApi getLearnedWords();
}
