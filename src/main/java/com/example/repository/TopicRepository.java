package com.example.repository;

import com.example.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author BAO 7/5/2023
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    @Query("SELECT t from  Topic t WHERE t.course.id = ?1")
    List<Topic> findByCourseId(int courseId);

    @Query("SELECT t FROM Topic t LEFT JOIN FETCH t.vocabulary LEFT JOIN FETCH t.course")
    List<Topic> findAll();
}
