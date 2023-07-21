package com.example.mapper.impl;

import com.example.dto.admin.AdminCourseRes;
import com.example.dto.user.UserCourseResponse;
import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * @author BAO 7/19/2023
 */
@Component
public class CourseMapperImpl implements CourseMapper {
  @Override
  public UserCourseResponse courseToUserCourseResponse(Course course) {
    UserCourseResponse userCourseResponse = new UserCourseResponse();
    userCourseResponse.setId(course.getId());
    userCourseResponse.setDescription(course.getDescription());
    userCourseResponse.setTitle(course.getTitle());
    userCourseResponse.setTarget(course.getTarget());
    userCourseResponse.setImg(course.getImg());
    return userCourseResponse;
  }

  @Override
  public List<UserCourseResponse> coursesToUserCoursesResponse(List<Course> listCourses) {
    return listCourses.stream().map(this::courseToUserCourseResponse).toList();
  }

  @Override
  public AdminCourseRes courseToAdminCourseRes(Course course) {
    AdminCourseRes adminCourseRes = new AdminCourseRes();
    adminCourseRes.setId(course.getId());
    adminCourseRes.setDescription(course.getDescription());
    adminCourseRes.setTitle(course.getDescription());
    adminCourseRes.setTarget(course.getTarget());
    adminCourseRes.setImg(course.getImg());
    return adminCourseRes;
  }

  @Override
  public List<AdminCourseRes> coursesToAdminCoursesRes(List<Course> courseList) {
    return courseList.stream().map(this::courseToAdminCourseRes).collect(Collectors.toList());
  }
}
