package com.example.mapper;

import com.example.dto.user.UserAnswerResponse;
import com.example.entity.Answer;

import java.util.List;
import java.util.Set;

/**
 * @author BAO 7/20/2023
 */
public interface AnswerMapper {
    UserAnswerResponse answerToUserAnswerRes(Answer answer);
    Set<UserAnswerResponse> answersToUserAnswersRes(List<Answer> answers);


}
