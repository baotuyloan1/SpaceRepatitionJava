package com.example.controller;

import com.example.entity.Course;
import com.example.service.CourseService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author BAO 7/5/2023
 */
@RestController
@RequestMapping("/api/courses")
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping({"/", ""})
  public ResponseEntity<List<Course>> getAllCourses() {
    return new ResponseEntity<>(courseService.listCourse(), HttpStatus.OK);
  }

  @PostMapping({"/", ""})
  public ResponseEntity<Course> createCourse(
      @RequestPart("course") Course course, @RequestPart("img") MultipartFile img) {
    return new ResponseEntity<>(courseService.save(course, img), HttpStatus.CREATED);
  }


}
