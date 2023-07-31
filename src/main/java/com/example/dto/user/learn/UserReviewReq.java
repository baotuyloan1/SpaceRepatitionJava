package com.example.dto.user.learn;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ADMIN 7/27/2023
 */
@Setter
@Getter
public class UserReviewReq {
  private long vocabularyId;
  private String answer;
}
