package com.example.mapper.impl;

import com.example.dto.user.UserAnswerResponse;
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
  public UserAnswerResponse answerToUserAnswerRes(Answer answer) {
    UserAnswerResponse userAnswerResponse = new UserAnswerResponse();
    userAnswerResponse.setAnswer(answer.getAnswer());
    userAnswerResponse.setId(answer.getId());
    return userAnswerResponse;
  }

  @Override
  public Set<UserAnswerResponse> answersToUserAnswersRes(List<Answer> answers) {
    return answers.stream().map(this::answerToUserAnswerRes).collect(Collectors.toSet());
  }
}
