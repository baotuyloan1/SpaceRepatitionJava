package com.example.mapper;

import com.example.dto.user.QuestionUserResponse;
import com.example.entity.Question;

/**
 * @author BAO 7/20/2023
 */
public interface QuestionMapper {

    QuestionUserResponse questionToQuestionUserRes(Question question);
}
