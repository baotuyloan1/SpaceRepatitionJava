package com.example.service.impl;

import com.example.entity.Question;
import com.example.repository.QuestionRepository;
import com.example.service.QuestionService;
import org.springframework.stereotype.Service;

/**
 * @author BAO 7/8/2023
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }
}
