package com.example.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/20/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerResponse {
  private long id;
  private String answer;
}
