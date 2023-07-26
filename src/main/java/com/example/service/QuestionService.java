package com.example.service;

import com.example.dto.admin.QuestionRequestDto;
import com.example.dto.admin.AdminQuestionRes;

import java.util.List;

/**
 * @author BAO 7/8/2023
 */


public interface QuestionService {
    Long save(QuestionRequestDto question);

    List<AdminQuestionRes> listQuestion();
    List<AdminQuestionRes> adminQuestionsByWordId(long wordId);

    void deleteQuestion(Long questionId);

    AdminQuestionRes findById(Long questionId);

}
