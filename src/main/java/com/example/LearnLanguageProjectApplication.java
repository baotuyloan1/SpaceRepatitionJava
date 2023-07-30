package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LearnLanguageProjectApplication {

  public static void main(String[] args) {
    SpringApplication springApplication =
        new SpringApplication(LearnLanguageProjectApplication.class);
    springApplication.setAdditionalProfiles("dev");
    springApplication.run(args);
  }
}
