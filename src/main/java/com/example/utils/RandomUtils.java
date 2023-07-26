package com.example.utils;

import java.util.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ADMIN 7/26/2023
 */
@Configuration
public class RandomUtils {

  @Bean
  public Random random() {
    return new Random();
  }
}
