package com.example.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/14/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVocabularyRequest {

  private long idVocabulary;

  private boolean rightAnswer;
}
