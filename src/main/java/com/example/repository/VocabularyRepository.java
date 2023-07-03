package com.example.repository;

import com.example.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author BAO
 * 6/29/2023
 */
@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    @Query("SELECT v FROM Vocabulary v WHERE v.id NOT IN (SELECT uv.vocabulary.id FROM UserVocabulary uv WHERE  uv.user.id = ?1)")
    List<Vocabulary> findAllNotLearnedBy(Long userId);

}
