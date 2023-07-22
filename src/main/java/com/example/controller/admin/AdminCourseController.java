package com.example.controller.admin;

import com.example.dto.admin.AdminCourseRes;
import com.example.dto.admin.AdminTopicRes;
import com.example.entity.Course;
import com.example.service.CourseService;
import java.sql.SQLException;
import java.util.List;

import com.example.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author BAO 7/5/2023
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/courses/ahihi")
public class AdminCourseController {

  private final CourseService courseService;
  private final TopicService topicService;


  @GetMapping
  public ResponseEntity<List<AdminCourseRes>> getAllCourses() {
    return new ResponseEntity<>(courseService.listCourse(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Integer> createCourse(
      @RequestPart("course") Course course, @RequestPart("img") MultipartFile img) {
    return new ResponseEntity<>(courseService.save(course, img).getId(), HttpStatus.CREATED);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteCourse(@PathVariable("id") int courseId) throws SQLException {
    boolean isDelete = courseService.deleteCourseById(courseId);
    if (isDelete) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    else {
      throw new SQLException("Can't delete course");
    }
  }

  @GetMapping("{id}/topics")
  public ResponseEntity<List<AdminTopicRes>> getTopics(@PathVariable("id") int courseId) {
    return new ResponseEntity<>(topicService.adminFindByCourseId(courseId), HttpStatus.OK);
  }
}
