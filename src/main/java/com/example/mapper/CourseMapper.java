package com.example.mapper;

import com.example.dto.user.UserCourseResponse;
import com.example.entity.Course;

import java.util.List;

/**
 * @author BAO 7/19/2023
 */
public interface CourseMapper {

  UserCourseResponse courseToCourseUserResponse(Course course);

  List<UserCourseResponse> coursesToCoursesUserResponse(List<Course> listCourses);
}
