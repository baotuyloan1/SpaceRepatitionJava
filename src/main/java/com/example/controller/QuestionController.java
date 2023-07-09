package com.example.controller;

import com.example.entity.Question;
import com.example.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author BAO 7/8/2023
 */
@RestController
@RequestMapping("/api/questions")
public class QuestionController {


    private final QuestionService questionService;

    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @PostMapping({"/",""})
    public ResponseEntity<Question> saveQuestion(@RequestBody Question question){
      Question question1=   questionService.save(question);

    return new ResponseEntity<>(question1, HttpStatus.CREATED);
    }
}
