package com.example.repository;

import com.example.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author BAO 7/5/2023
 */

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {}
