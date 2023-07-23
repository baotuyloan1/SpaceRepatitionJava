package com.example.repository;

import com.example.entity.Vocabulary;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
  List<Vocabulary> findVocabulariesNotLearnedByUserIdInTopicId(Long userId, int topicId);

  @Query(
      "SELECT v FROM Vocabulary v JOIN UserVocabulary uv ON v.id=uv.id.vocabularyId JOIN Topic t ON v.topic.id=t.id WHERE t.id = :topicId AND  uv.id.userId = :userid")
  List<Vocabulary> findLearnedWordByTopicAndUserId(
      @Param("topicId") int topicId, @Param("userid") Long userId);

  @Query("SELECT v FROM Vocabulary v INNER JOIN  v.topic t WHERE t.id = ?1")
  List<Vocabulary> findVocabulariesByTopicId(int topicId);
}
