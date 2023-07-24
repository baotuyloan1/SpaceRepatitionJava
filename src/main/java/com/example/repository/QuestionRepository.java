package com.example.repository;

import com.example.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author BAO 7/8/2023
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
  @Query("SELECT q FROM Question q JOIN q.vocabulary  WHERE q.vocabulary.id = ?1")
  List<Question> findByWordId(long wordId);
}
