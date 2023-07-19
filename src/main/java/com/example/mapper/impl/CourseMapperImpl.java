package com.example.mapper.impl;

import com.example.dto.user.UserCourseResponse;
import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BAO 7/19/2023
 */
@Component
public class CourseMapperImpl implements CourseMapper {
  @Override
  public UserCourseResponse courseToCourseUserResponse(Course course) {
    UserCourseResponse userCourseResponse = new UserCourseResponse();
    userCourseResponse.setId(course.getId());
    userCourseResponse.setDescription(course.getDescription());
    userCourseResponse.setTitle(course.getTitle());
    userCourseResponse.setTarget(course.getTarget());
    userCourseResponse.setImg(course.getImg());
    return userCourseResponse;
  }

  @Override
  public List<UserCourseResponse> coursesToCoursesUserResponse(List<Course> listCourses) {
    return listCourses.stream().map(this::courseToCourseUserResponse).collect(Collectors.toList());
  }
}
