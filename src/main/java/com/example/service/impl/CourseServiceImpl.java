package com.example.service.impl;

import com.example.config.PropertiesConfig;
import com.example.dto.admin.AdminCourseRes;
import com.example.dto.user.UserCourseRes;
import com.example.dto.user.UserTopicRes;
import com.example.entity.Course;
import com.example.exception.ApiExceptionRes;
import com.example.exception.ApiRequestException;
import com.example.mapper.CourseMapper;
import com.example.repository.CourseRepository;
import com.example.service.CourseService;
import com.example.utils.FileUtils;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author BAO 7/5/2023
 */
@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;
  private final FileUtils fileUtils;
  private final PropertiesConfig env;

  @Override
  public Page<Course> pageCourses(int pageSize, int currentPage, String sortDir, String sortField) {
    Pageable pageable =
        PageRequest.of(
            currentPage - 1,
            pageSize,
            sortDir.equals("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
    return courseRepository.findAll(pageable);
  }

  @Transactional
  @Override
  public List<AdminCourseRes> adminGetCourses() {
    List<Course> courseList = courseRepository.findAll();
    return courseMapper.coursesToAdminCoursesRes(courseList);
  }

  @Override
  public List<UserCourseRes> userGetCourses() {
    List<Course> courses = courseRepository.findAll();
    return courseMapper.coursesToUserCoursesResponse(courses);
  }


  @Transactional
  @Override
  public Course save(Course course, MultipartFile img) {
    Course savedCourse = courseRepository.save(course);
    try {
      String fileName =
          fileUtils.saveFile(img, savedCourse.getId(), env.getPathImgCourse(), "category");
      savedCourse.setImg(fileName);
    } catch (IOException e) {
      throw new ApiRequestException(e, e.getMessage());
    }
    return courseRepository.save(savedCourse);
  }

  @Override
  public void deleteCourseById(int courseId) {
    courseRepository.deleteById(courseId);
  }
}
