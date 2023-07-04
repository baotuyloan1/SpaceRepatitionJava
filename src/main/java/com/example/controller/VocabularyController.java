package com.example.controller;

import com.example.dto.DefaultFormatValidate;
import com.example.entity.Vocabulary;
import com.example.service.FileService;
import com.example.service.VocabularyService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author BAO 6/29/2023
 */
@RestController
@RequestMapping("/api/vocabularies")
@CrossOrigin(origins = "http://localhost:3000")
public class VocabularyController {

  @Value("${dir.resource.audioWord}")
  private String pathAudioWord;

  @Value("${dir.resource.audioSentence}")
  private String pathAudioSentence;

  @Value("${dir.resource.imgWord}")
  private String pathImg;

  private final VocabularyService vocabularyService;
  private final FileService fileService;

  private final String DEFAULT_MESSAGE_FILE_NULL = "must not be null";

  public VocabularyController(VocabularyService vocabularyService, FileService fileService) {
    this.vocabularyService = vocabularyService;
    this.fileService = fileService;
  }

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
  public ResponseEntity<List<Vocabulary>> getAllAudios() {
    return new ResponseEntity<>(vocabularyService.getAllVocabulary(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public Vocabulary getAudioById(@PathVariable Long id) {
    return vocabularyService.getById(id);
  }

  //      @GetMapping(value = "/playAudio/{id}", produces = MediaType.ALL_VALUE)
  @GetMapping(value = "/playAudio/{fileName}")
  public void playAudio(@PathVariable String fileName, HttpServletResponse response) {

    String type = fileName.substring((fileName.indexOf("_") + 1), fileName.indexOf("."));
    String path = null;
    if (type.equals("word")) {
      path = pathAudioWord;
    } else if (type.equals("sentence")) {
      path = pathAudioSentence;
    }
    InputStream resource = null;
    try {
      resource = fileService.getAudioFile(path, fileName);
      response.setContentType(MediaType.ALL_VALUE);
      StreamUtils.copy(resource, response.getOutputStream());
    } catch (IOException ex) {
      throw new RuntimeException("Something went wrong", ex);
    }
  }

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

  @GetMapping("/learn")
  public List<Map<String, Object>> listVocabulary() {
    List<Vocabulary> vocabularyList = vocabularyService.getVocabularyNotLearnedByUserId(1);

    List<Map<String, Object>> objectWords = new LinkedList<>();

    for (Vocabulary vocabulary : vocabularyList) {

      Map<String, Object> word = new LinkedHashMap<>();
      word.put("id", vocabulary.getId());
      word.put("word", vocabulary);


      List<Object> learningTypes = new LinkedList<>();


      Map<String, Object> map = new LinkedHashMap<>();


      /** Selection type */
      // dùng foreach lấy từ bảng ra các đáp án của question id
      List<String> listAnswer = new ArrayList();
      listAnswer.add("Xin chào");
      listAnswer.add("Tạm biệt");
      listAnswer.add("Con ngựa");

      map.put("id", 1);
      map.put("type", "select");
      map.put("question", "Hello My name is Bao?");
      map.put("rightQuestionId", 1);
      map.put("answers", listAnswer);
      learningTypes.add(map);

      /** Nghe phát âm từ và điền lại từ cho đúng */
      Map<String, Object> listenMap = new LinkedHashMap<>();
      listenMap.put("id", 2);
      listenMap.put("type", "listen");
      learningTypes.add(listenMap);

      /** Hiển thị nghĩa của từ và ghi lại từ */
      Map<String, Object> meanMap = new LinkedHashMap<>();
      meanMap.put("id", 3);
      meanMap.put("type", "mean");

      learningTypes.add(meanMap);
      word.put("learningTypes",learningTypes);

      objectWords.add(word);

    }

    return objectWords;
  }
}
