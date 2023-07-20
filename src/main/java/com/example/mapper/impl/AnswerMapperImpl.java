package com.example.mapper.impl;

import com.example.dto.user.UserAnswerReponse;
import com.example.entity.Answer;
import com.example.mapper.AnswerMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author BAO 7/20/2023
 */
@Component
public class AnswerMapperImpl implements AnswerMapper {
  @Override
  public UserAnswerReponse answerToUserAnswerRes(Answer answer) {
    UserAnswerReponse userAnswerReponse = new UserAnswerReponse();
    userAnswerReponse.setAnswer(answer.getAnswer());
    userAnswerReponse.setId(answer.getId());
    return userAnswerReponse;
  }

  @Override
  public Set<UserAnswerReponse> answersToUserAnswersRes(List<Answer> answers) {
    return answers.stream().map(this::answerToUserAnswerRes).collect(Collectors.toSet());
  }
}
