package com.example.repository;

import com.example.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author BAO 7/5/2023
 */
public interface ChapterRepository extends JpaRepository<Chapter, Long> {}
