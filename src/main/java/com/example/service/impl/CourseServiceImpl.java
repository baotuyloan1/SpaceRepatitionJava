package com.example.service.impl;

import com.example.entity.Course;
import com.example.exception.CustomerException;
import com.example.repository.CourseRepository;
import com.example.service.CourseService;
import com.example.utils.FileUtils;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
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
public class CourseServiceImpl implements CourseService {

  @Value("${dir.resource.imgCourse}")
  private String dirImgCourses;

  private final CourseRepository courseRepository;
  private final FileUtils fileUtils;

  public CourseServiceImpl(CourseRepository courseRepository, FileUtils fileUtils) {
    this.courseRepository = courseRepository;
    this.fileUtils = fileUtils;
  }

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

  @Override
  public List<Course> listCourse() {
    return courseRepository.findAll();
  }


  /**
   * If no custom rollback rules apply, the transaction will roll back on RuntimeException and Error
   * but not on checked exceptions
   *
   * chi mỗi method trong @Service mới có mặc định là @Transactional
   *
   * @param course
   * @param img
   * @return
   */
  @Override
  public Course save(Course course, MultipartFile img) {
    Course savedCourse = courseRepository.save(course);
    try {
      String fileName = fileUtils.saveFile(img, savedCourse.getId(), dirImgCourses, "category");
      savedCourse.setImg(fileName);
    } catch (IOException e) {
      throw new CustomerException(e, "Something went wrong");
    }
    return courseRepository.save(savedCourse);
  }
}
