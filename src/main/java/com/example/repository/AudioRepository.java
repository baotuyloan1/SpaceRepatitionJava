package com.example.repository;

import com.example.entity.AudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author BAO
 * 6/29/2023
 */
@Repository
public interface AudioRepository extends JpaRepository<AudioEntity, Long> {


}
