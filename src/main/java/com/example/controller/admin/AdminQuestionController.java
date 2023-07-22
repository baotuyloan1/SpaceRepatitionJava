package com.example.controller.admin;

import com.example.dto.QuestionRequestDto;
import com.example.dto.admin.AdminQuestionRes;
import com.example.entity.Question;
import com.example.service.QuestionService;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author BAO 7/8/2023
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/questions")
public class AdminQuestionController {

  private final QuestionService questionService;


  @PostMapping
  public ResponseEntity<AdminQuestionRes> createQuestion(
      @RequestBody QuestionRequestDto questionRequestDto) {
    return new ResponseEntity<>(questionService.save(questionRequestDto), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Question>> getAllQuestions() {
    return new ResponseEntity<>(questionService.listQuestion(), HttpStatus.OK);
  }

  @DeleteMapping({"{id}"})
  public ResponseEntity<Void> deleteQuestion(@PathVariable("id") Long questionId) {
    questionService.deleteQuestion(questionId);
    return new ResponseEntity<>( HttpStatus.NO_CONTENT);
  }

  @GetMapping({"{id}"})
  public ResponseEntity<Question> getQuestionById(@PathVariable("id") Long questionId) {
    return new ResponseEntity<>(questionService.findById(questionId), HttpStatus.OK);
  }
}