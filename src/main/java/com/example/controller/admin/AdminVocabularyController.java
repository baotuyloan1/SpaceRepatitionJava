package com.example.controller.admin;

import com.example.config.PropertiesConfig;
import com.example.dto.DefaultFormatValidate;
import com.example.dto.admin.AdminVocabularyRes;
import com.example.entity.Vocabulary;
import com.example.service.FileService;
import com.example.service.VocabularyService;
import com.example.utils.FileUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.*;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author BAO 6/29/2023
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/vocabularies")
public class AdminVocabularyController {

  private final VocabularyService vocabularyService;
  private final FileUtils fileUtils;

  private final String DEFAULT_MESSAGE_FILE_NULL = "must not be null";

  @PostMapping({"/", ""})
  public ResponseEntity<?> saveWord(
      @Valid @RequestPart(value = "vocabulary", required = false) Vocabulary vocabulary,
      BindingResult bindingResult,
      @RequestPart(value = "audioWord", required = false) MultipartFile audioWord,
      @RequestPart(value = "audioSentence", required = false) MultipartFile audioSentence,
      @RequestPart(value = "img", required = false) MultipartFile img) {
    if (audioWord == null) {
      bindingResult.addError(new FieldError("audioWord", "audioWord", DEFAULT_MESSAGE_FILE_NULL));
    }
    if (audioSentence == null) {
      bindingResult.addError(
          new FieldError("audioSentence", "audioSentence", DEFAULT_MESSAGE_FILE_NULL));
    }
    if (img == null) {
      bindingResult.addError(new FieldError("img", "img", DEFAULT_MESSAGE_FILE_NULL));
    }
    if (bindingResult.hasErrors()) {

      return new ResponseEntity<>(
          new DefaultFormatValidate(bindingResult.getAllErrors()), HttpStatus.NOT_ACCEPTABLE);
    }
    return new ResponseEntity<>(
        vocabularyService.createWord(vocabulary, audioWord, audioSentence, img), HttpStatus.OK);
  }

  @GetMapping({"/", ""})
  public ResponseEntity<List<AdminVocabularyRes>> getAllVocabulary() {
    return new ResponseEntity<>(vocabularyService.getAllVocabulary(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public Vocabulary getAudioById(@PathVariable Long id) {
    return vocabularyService.getById(id);
  }

  //      @GetMapping(value = "/playAudio/{id}", produces = MediaType.ALL_VALUE)

  // Api for uploading audio after posting titles and other audio
  //    @PostMapping("/post/{id}")
  //    public WordEntity uploadingAudio(@RequestParam("audio") MultipartFile audio, @PathVariable
  // Long id) throws IOException {
  //        WordEntity wordEntity = wordService.getById(id);
  //        FileModel fileModel = fileService.uploadAudio(path, audio);
  //        wordEntity.setAudioName(fileModel.getAudioFileName());
  //        return wordService.update(wordEntity, audio);
  //    }

  @PutMapping(value = {"", "/"})
  public void updateWord(
      @RequestBody Vocabulary vocabulary, @RequestParam("audio") MultipartFile audio) {
    vocabularyService.update(vocabulary, audio);
  }

  @GetMapping(value = "/playAudio/{fileName}")
  public void playAudio(@PathVariable String fileName, HttpServletResponse response) {
    fileUtils.playAudio(fileName, response);
  }
}
