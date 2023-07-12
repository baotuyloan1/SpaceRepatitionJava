package com.example.repository;

import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author BAO 7/3/2023
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName (String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);
}
