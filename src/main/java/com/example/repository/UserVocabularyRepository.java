package com.example.repository;

import com.example.entity.UserVocabulary;
import com.example.entity.UserVocabularyId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author BAO 7/3/2023
 */
public interface UserVocabularyRepository extends JpaRepository<UserVocabulary, UserVocabularyId> {}
