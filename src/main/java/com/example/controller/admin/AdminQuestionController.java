package com.example.controller.admin;

import com.example.dto.QuestionRequestDto;
import com.example.entity.Question;
import com.example.service.QuestionService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author BAO 7/8/2023
 */
@RestController
@RequestMapping("/api/admin/questions")
public class AdminQuestionController {

  private final QuestionService questionService;

  public AdminQuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @PostMapping({"/", ""})
  public ResponseEntity<Question> saveQuestion(@RequestBody QuestionRequestDto questionRequestDto) {
    Question question1 = questionService.save(questionRequestDto);

    return new ResponseEntity<>(question1, HttpStatus.CREATED);
  }

  @GetMapping({"/", ""})
  public ResponseEntity<List<Question>> getAllQuestions() {
    return new ResponseEntity<>(questionService.listQuestion(), HttpStatus.OK);
  }

  @DeleteMapping({"/{id}"})
  public ResponseEntity<Void> deleteQuestion(@PathVariable("id") Long questionId) {
    questionService.deleteQuestion(questionId);
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  @GetMapping({"/{id}"})
  public ResponseEntity<Question> getQuestionById(@PathVariable("id") Long questionId) {
    return new ResponseEntity<>(questionService.findById(questionId), HttpStatus.OK);
  }
}
