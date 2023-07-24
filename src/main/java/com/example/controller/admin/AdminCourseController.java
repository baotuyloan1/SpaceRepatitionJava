package com.example.controller.admin;

import com.example.dto.admin.AdminCourseRes;
import com.example.dto.admin.AdminTopicRes;
import com.example.entity.Course;
import com.example.service.CourseService;
import com.example.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author BAO 7/5/2023
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/courses")
public class AdminCourseController {

  private final CourseService courseService;
  private final TopicService topicService;

  @GetMapping
  public ResponseEntity<List<AdminCourseRes>> getAllCourses() {
    return new ResponseEntity<>(courseService.adminGetCourses(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Integer> createCourse(
      @RequestPart("course") Course course, @RequestPart("img") MultipartFile img) {
    return new ResponseEntity<>(courseService.save(course, img).getId(), HttpStatus.CREATED);
  }

//  @DeleteMapping("{id}")
//  public ResponseEntity<Void> deleteCourse(@PathVariable("id") int courseId) {
//    courseService.deleteCourseById(courseId);
//    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//  }

  @GetMapping("{id}/topics")
  public ResponseEntity<List<AdminTopicRes>> getTopics(@PathVariable("id") int courseId) {
    return new ResponseEntity<>(topicService.adminFindByCourseId(courseId), HttpStatus.OK);
  }
}
