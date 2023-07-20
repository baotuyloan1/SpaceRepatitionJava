package com.example.controller.admin;

import com.example.entity.Course;
import com.example.exception.ErrorResponse;
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
@RequestMapping("/api/admin/courses")
public class AdminCourseController {

  private final CourseService courseService;

  public AdminCourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping({"/", ""})
  public ResponseEntity<List<Course>> getAllCourses() {
    List<Course> courseList = courseService.listCourse();
    return new ResponseEntity<>(courseList, HttpStatus.OK);
  }

  @PostMapping({"/", ""})
  public ResponseEntity<Course> createCourse(
      @RequestPart("course") Course course, @RequestPart("img") MultipartFile img) {
    return new ResponseEntity<>(courseService.save(course, img), HttpStatus.CREATED);
  }

  @DeleteMapping({"/{id}"})
  public ResponseEntity<?> deleteCourse(@PathVariable("id") int courseId) {
    boolean isDelete = courseService.deleteCourseById(courseId);
    if (isDelete) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    else {
      ErrorResponse errorResponse =
          new ErrorResponse(
              "error",
              HttpStatus.NOT_ACCEPTABLE.value(),
              HttpStatus.NOT_ACCEPTABLE.toString(),
              "Something went wrong");
      return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }
  }
}
