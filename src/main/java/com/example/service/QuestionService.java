package com.example.service;

import com.example.dto.QuestionRequestDto;
import com.example.entity.Question;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author BAO 7/8/2023
 */


public interface QuestionService {
    Question save(QuestionRequestDto question);

    List<Question> listQuestion();

    void deleteQuestion(Long questionId);

    Question findById(Long questionId);
}
