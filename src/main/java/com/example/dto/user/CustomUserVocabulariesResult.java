package com.example.dto.user;

import com.example.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ADMIN 7/31/2023
 */
@Setter
@Getter
public class CustomUserVocabulariesResult {
  private Long count;
  private User user;

  public CustomUserVocabulariesResult(Long count, User user) {
    this.count = count;
    this.user = user;
  }
}
