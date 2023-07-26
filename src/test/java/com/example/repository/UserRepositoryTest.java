package com.example.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author ADMIN 7/26/2023
 */
@DataJpaTest
class UserRepositoryTest {

  @Autowired private UserRepository userRepository;
  @Autowired private UserRepository underTest;


  @AfterEach
  void tearDown() {
    underTest.deleteAll();
  }

  @Test
  void itShouldCheckWhenUserExitsByUsername() {
    // give
    String userName = "baotuyloan";
    User user = new User();
    user.setUsername(userName);
    user.setEmail("bao@gmail.com");
    underTest.save(user);

    // when
    boolean expected = underTest.existsByUsername(userName);
    // then
    assertThat(expected).isTrue();
  }

  @Test
  void itShouldCheckWhenUserEmailDoesNotExists() {
    // give
    String userName = "baotuyloan";

    // when
    boolean expected = underTest.existsByUsername(userName);
    // then
    assertThat(expected).isFalse();
  }
}
