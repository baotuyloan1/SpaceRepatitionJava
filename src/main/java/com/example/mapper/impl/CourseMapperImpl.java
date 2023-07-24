package com.example.mapper.impl;

import com.example.dto.admin.AdminCourseRes;
import com.example.dto.user.UserCourseRes;
import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * @author BAO 7/19/2023
 */
@Component("courseMapper")
public class CourseMapperImpl implements CourseMapper {
  @Override
  public UserCourseRes courseToUserCourseResponse(Course course) {
    UserCourseRes userCourseRes = new UserCourseRes();
    userCourseRes.setId(course.getId());
    userCourseRes.setDescription(course.getDescription());
    userCourseRes.setTitle(course.getTitle());
    userCourseRes.setTarget(course.getTarget());
    userCourseRes.setImg(course.getImg());
    return userCourseRes;
  }

  @Override
  public List<UserCourseRes> coursesToUserCoursesResponse(List<Course> listCourses) {
    return listCourses.stream().map(this::courseToUserCourseResponse).toList();
  }

  @Override
  public AdminCourseRes courseToAdminCourseRes(Course course) {
    AdminCourseRes adminCourseRes = new AdminCourseRes();
    adminCourseRes.setId(course.getId());
    adminCourseRes.setDescription(course.getDescription());
    adminCourseRes.setTitle(course.getTitle());
    adminCourseRes.setTarget(course.getTarget());
    adminCourseRes.setImg(course.getImg());
    return adminCourseRes;
  }

  @Override
  public List<AdminCourseRes> coursesToAdminCoursesRes(List<Course> courseList) {
    return courseList.stream().map(this::courseToAdminCourseRes).collect(Collectors.toList());
  }
}
