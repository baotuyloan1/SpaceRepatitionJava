package com.example.repository;

import com.example.entity.Vocabulary;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author BAO 6/29/2023
 */
@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
  //
  @Transactional
  @Query(
      "SELECT  v FROM Vocabulary v LEFT JOIN  FETCH v.question q "
          + "LEFT JOIN FETCH  v.topic t "
          + "WHERE v.id NOT IN (SELECT uv.vocabulary.id FROM UserVocabulary uv "
          + "WHERE  uv.user.id = ?1) AND t.id = ?2")
  List<Vocabulary> findAllNotLearnedBy(Long userId, int topicId);
}
