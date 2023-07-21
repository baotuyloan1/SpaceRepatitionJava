package com.example.mapper;

import com.example.dto.user.UserAnswerReponse;
import com.example.entity.Answer;

import java.util.List;
import java.util.Set;

/**
 * @author BAO 7/20/2023
 */
public interface AnswerMapper {
    UserAnswerReponse answerToUserAnswerRes(Answer answer);
    Set<UserAnswerReponse> answersToUserAnswersRes(List<Answer> answers);


}
