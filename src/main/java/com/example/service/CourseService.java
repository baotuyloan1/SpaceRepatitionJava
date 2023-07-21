package com.example.service;

import com.example.dto.admin.AdminCourseRes;
import com.example.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author BAO 7/5/2023
 */
public interface CourseService {

    Page<Course> pageCourses(int pageSize, int currentPage, String sortDir, String sortField);

    List<AdminCourseRes> listCourse();

    AdminCourseRes save(Course course, MultipartFile img);

    boolean deleteCourseById(int courseId);
}
