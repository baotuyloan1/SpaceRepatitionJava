package com.example.dto.user;

import java.util.Set;

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
public class TypeQuestionRes extends TypeLearnRes {

  private long idQuestion;
  private String question;
  private long idRightAnswer;
  private Set<UserAnswerReponse> answers;
  public TypeQuestionRes(String type, long idQuestion, String question, long idRightAnswer, Set<UserAnswerReponse> answers) {
    super(type);
    this.idQuestion = idQuestion;
    this.question = question;
    this.idRightAnswer = idRightAnswer;
    this.answers = answers;
  }
}
