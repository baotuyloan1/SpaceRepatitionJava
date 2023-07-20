package com.example.service.impl;

import com.example.entity.Course;
import com.example.entity.Topic;
import com.example.exception.CustomerException;
import com.example.repository.CourseRepository;
import com.example.service.CourseService;
import com.example.utils.FileUtils;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
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

  private final TransactionTemplate entityManager;

  public CourseServiceImpl(CourseRepository courseRepository, FileUtils fileUtils, EntityManager entityManager, TransactionTemplate entityManager1) {
    this.courseRepository = courseRepository;
    this.fileUtils = fileUtils;
    this.entityManager = entityManager1;
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

  @Transactional
  @Override
  public List<Course> listCourse() {
    List<Course> a = courseRepository.findAll();
    a.stream().map(Course::getTopic).forEach(Hibernate::initialize);
    return a;
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

  @Override
  public boolean deleteCourseById(int courseId) {
       courseRepository.deleteById(courseId);
      return  !courseRepository.existsById(courseId);
  }
}
