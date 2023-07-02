package com.example.repository;

import com.example.entity.VocabularyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author BAO
 * 6/29/2023
 */
@Repository
public interface VocabularyRepository extends JpaRepository<VocabularyEntity, Long> {


}
