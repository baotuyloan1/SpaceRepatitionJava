package com.example.controller.admin;

import com.example.dto.admin.AdminTopicRes;
import com.example.dto.admin.AdminVocabularyRes;
import com.example.entity.Topic;
import com.example.entity.Vocabulary;
import com.example.service.TopicService;
import java.util.List;

import com.example.service.VocabularyService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author BAO 7/5/2023
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/topics")
public class AdminTopicController {

  private final TopicService topicService;
  private final VocabularyService vocabularyService;

  @PostMapping
  public ResponseEntity<Integer> saveTopic(
      @RequestPart("topic") Topic topic, @RequestPart MultipartFile img) {
    Topic savedTopic = topicService.save(topic, img);
    return new ResponseEntity<>(savedTopic.getId(), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<AdminTopicRes>> getAll() {
    return new ResponseEntity<>(topicService.listAll(), HttpStatus.OK);
  }

//  @Transactional
//  @DeleteMapping("{id}")
//  public ResponseEntity<Void> deleteTopicById(@PathVariable("id") int id) {
//    topicService.deleteTopicById(id);
//    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//  }

  @GetMapping("{id}/vocabularies")
  public ResponseEntity<List<AdminVocabularyRes>> getVocabulariesByTopicId(
      @PathVariable("id") int id) {
    List<AdminVocabularyRes> vocabularies = vocabularyService.getVocabulariesByTopicId(id);
    return new ResponseEntity<>(vocabularies, HttpStatus.OK);
  }
}
