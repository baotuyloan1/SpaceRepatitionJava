package com.example.controller.admin;

import com.example.dto.admin.AdminTopicRes;
import com.example.entity.Topic;
import com.example.service.TopicService;
import java.util.List;

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


  @PostMapping
  public ResponseEntity<Topic> saveTopic(
      @RequestPart("topic") Topic topic, @RequestPart MultipartFile img) {
    Topic topic1 = topicService.save(topic);
    return new ResponseEntity<>(topic1, HttpStatus.CREATED);
  }




  @GetMapping
  public ResponseEntity<List<AdminTopicRes>> getAll(){
    return new ResponseEntity<>(topicService.listAll(),HttpStatus.OK);
  }



  @DeleteMapping("{id}")
  public ResponseEntity<Void>deleteTopicById (@PathVariable("id") int id){
    topicService.deleteTopicById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
