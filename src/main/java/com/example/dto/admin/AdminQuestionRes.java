package com.example.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author BAO 7/21/2023
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminQuestionRes {
  private long id;
  private String question;
  private AdminAnswerRes rightAnswer;
  private AdminVocabularyRes vocabulary;
  private List<AdminAnswerRes> answers;
}
