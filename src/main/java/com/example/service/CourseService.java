package com.example.service;

import com.example.dto.admin.AdminCourseRes;
import com.example.dto.user.UserCourseRes;
import com.example.dto.user.UserTopicRes;
import com.example.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author BAO 7/5/2023
 */
public interface CourseService {

    Page<Course> pageCourses(int pageSize, int currentPage, String sortDir, String sortField);

    List<AdminCourseRes> adminGetCourses();
    List<UserCourseRes> userGetCourses();


    Course save(Course course, MultipartFile img);

    void deleteCourseById(int courseId);
}
