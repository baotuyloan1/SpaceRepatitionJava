package com.example.repository;

import com.example.entity.UserVocabulary;
import com.example.entity.UserVocabularyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author BAO 7/3/2023
 */
@Repository
public interface UserVocabularyRepository extends JpaRepository<UserVocabulary, UserVocabularyId> {}
