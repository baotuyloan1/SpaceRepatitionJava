package com.example.controller.user;

import com.example.dto.user.*;
import com.example.dto.user.learn.*;
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
  public ResponseEntity<List<UserCourseRes>> getAllCourses() {
    return new ResponseEntity<>(courseService.userGetCourses(), HttpStatus.OK);
  }

  @GetMapping("/courses/{id}/topics")
  public ResponseEntity<List<UserTopicRes>> getByIdCourse(@PathVariable("id") int courseId) {
    return new ResponseEntity<>(topicService.userGetTopics(courseId), HttpStatus.OK);
  }

  @GetMapping("/topics/{id}/vocabularies")
  public ResponseEntity<List<UserLearnRes>> listVocabulary(@PathVariable("id") int topicId) {
    return new ResponseEntity<>(
        userVocabularyService.getVocabulariesByTopicId(topicId), HttpStatus.OK);
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

  @PutMapping("/review-meaning")
  public ResponseEntity<UserReviewRes> reviewMeaning(@RequestBody UserReviewMeaningReq req) {
    UserReviewRes res = userVocabularyService.updateReview(req);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @PostMapping("/updateVocabulary")
  public ResponseEntity<?> updateLearnedVocabulary(
      @RequestBody UserVocabularyRequest userVocabularyRequest) {
    //    userVocabularyService.updateLearnedVocabulary(userVocabularyRequest);
    return new ResponseEntity<>("Updated learned word", HttpStatus.OK);
  }

  @GetMapping("/getNextWordToReview")
  public ResponseEntity<List<UserLearnRes>> getTimeToReview() {
    return ResponseEntity.ok().body(userVocabularyService.getWordToReview());
  }

  @GetMapping("/info")
  public ResponseEntity<UserInfoResponse> getInfo(){
    return ResponseEntity.ok().body(userVocabularyService.getInfo());
  }



}
