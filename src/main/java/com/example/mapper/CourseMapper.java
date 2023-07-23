package com.example.mapper;

import com.example.dto.admin.AdminCourseRes;
import com.example.dto.user.UserCourseRes;
import com.example.entity.Course;

import java.util.List;

/**
 * @author BAO 7/19/2023
 */
public interface CourseMapper {

  UserCourseRes courseToUserCourseResponse(Course course);

  List<UserCourseRes> coursesToUserCoursesResponse(List<Course> listCourses);

  AdminCourseRes courseToAdminCourseRes (Course course);

  List<AdminCourseRes> coursesToAdminCoursesRes (List<Course> courseList);
}
