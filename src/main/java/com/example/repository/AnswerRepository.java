package com.example.repository;

import com.example.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author BAO 7/10/2023
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long > {}
