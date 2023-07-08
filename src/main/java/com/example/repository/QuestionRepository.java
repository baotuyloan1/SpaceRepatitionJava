package com.example.repository;

import com.example.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author BAO 7/8/2023
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {}
