package com.example.controller.user;

import com.example.dto.BaseResApi;
import com.example.dto.fcm.PushNotificationRequest;
import com.example.dto.user.*;
import com.example.dto.user.learn.*;
import com.example.dto.user.review.UserNextWordsReq;
import com.example.dto.user.review.UserReviewReq;
import com.example.dto.user.review.UserReviewRes;
import com.example.entity.UserVocabularyId;
import com.example.payload.response.UserInfoResponse;
import com.example.repository.VocabularyRepository;
import com.example.service.*;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

/**
 * @author BAO 7/3/2023
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final TopicService topicService;
  private final VocabularyService vocabularyService;
  private final UserVocabularyService userVocabularyService;
  private final CourseService courseService;
  private final VocabularyRepository vocabularyRepository;

  @GetMapping("/courses")
  public ResponseEntity<BaseResApi<List<UserCourseRes>>> getAllCourses() {
    List<UserCourseRes> list = courseService.userGetCourses();
    BaseResApi<List<UserCourseRes>> res = new BaseResApi<>("Get courses successfully !", list);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @GetMapping("/courses/{id}/topics")
  public ResponseEntity<BaseResApi<List<UserTopicRes>>> getByIdCourse(
      @PathVariable("id") int courseId) {
    List<UserTopicRes> list = topicService.userGetTopics(courseId);
    BaseResApi<List<UserTopicRes>> res = new BaseResApi<>("Get topics successfully !", list);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @GetMapping("/topics/{id}/vocabularies")
  public ResponseEntity<BaseResApi<List<UserLearnRes>>> listVocabulary(
      @PathVariable("id") int topicId) {
    List<UserLearnRes> list = userVocabularyService.getVocabulariesByTopicId(topicId);
    BaseResApi<List<UserLearnRes>> res = new BaseResApi<>("Get vocabularies successfully !", list);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  /**
   * Learn over three types must post to this method
   *
   * @param req
   * @return
   */
  @PostMapping("/learn-new")
  public ResponseEntity<UserVocabularyId> learnNewSelect(@RequestBody UserLearnNewReq req) {
    UserVocabularyId res = userVocabularyService.createUserVocabulary(req);
    return new ResponseEntity<>(res, HttpStatus.CREATED);
  }

  @PutMapping("/review-selection")
  public ResponseEntity<UserReviewRes> reviewSelection(@RequestBody UserReviewSelectionReq req) {
    UserReviewRes res = userVocabularyService.updateReviewSelect(req);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @PutMapping("/review-word")
  public ResponseEntity<UserReviewRes> reviewMeaning(@RequestBody UserReviewReq req) {
    UserReviewRes res = userVocabularyService.updateReview(req);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  //  @PutMapping("/update-vocabulary")
  //  public ResponseEntity<?> updateLearnedVocabulary(
  //      @RequestBody UserVocabularyRequest userVocabularyRequest) {
  //        userVocabularyService.updateLearnedVocabulary(userVocabularyRequest);
  //    return new ResponseEntity<>("Updated learned word", HttpStatus.OK);
  //  }

  @GetMapping("/next-word-review")
  public ResponseEntity<List<UserNextWordsReq>> getTimeToReview() {
    return ResponseEntity.ok().body(userVocabularyService.getWordToReview());
  }

  @GetMapping("/info")
  public ResponseEntity<UserInfoResponse> getInfo() {
    return ResponseEntity.ok().body(userVocabularyService.getInfo());
  }

  @PostMapping("/devices")
  public ResponseEntity<Void> saveTokenDevice(@RequestBody PushNotificationRequest req) {
    userVocabularyService.saveDeviceToken(req);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/learned-words")
  public ResponseEntity<BaseResApi> getLearnedWords() {
    BaseResApi learnedWords = userVocabularyService.getLearnedWords();
    return new ResponseEntity<>(learnedWords, HttpStatus.OK);
  }
}
