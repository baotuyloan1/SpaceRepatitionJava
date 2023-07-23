package com.example.service;

import com.example.dto.QuestionRequestDto;
import com.example.dto.admin.AdminCourseRes;
import com.example.dto.admin.AdminQuestionRes;
import com.example.dto.user.UserAnswerReponse;
import com.example.entity.Question;

import java.util.List;
import java.util.Set;

/**
 * @author BAO 7/8/2023
 */


public interface QuestionService {
    AdminQuestionRes save(QuestionRequestDto question);

    List<AdminQuestionRes> listQuestion();

    void deleteQuestion(Long questionId);

    AdminQuestionRes findById(Long questionId);

}
