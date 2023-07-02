package com.example.controller;

import com.example.entity.VocabularyEntity;
import com.example.service.FileService;
import com.example.service.VocabularyService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author BAO 6/29/2023
 */
@RestController
@RequestMapping("/api/vocabularies")
@CrossOrigin(origins = "http://localhost:3000")
public class VocabularyController {

  @Value("${project.audio}")
  private String path;

  private final VocabularyService vocabularyService;
  private final FileService fileService;

  public VocabularyController(VocabularyService vocabularyService, FileService fileService) {
    this.vocabularyService = vocabularyService;
    this.fileService = fileService;
  }

  @PostMapping({"/", ""})
  public ResponseEntity<?> saveWord(
      @Valid @RequestPart(value = "vocabulary", required = false) VocabularyEntity vocabulary,
      @Valid @RequestPart(value = "audioWord", required = false) MultipartFile audioWord,
      @Valid @RequestPart(value = "audioSentence", required = false) MultipartFile audiSentence,
      @Valid @RequestPart(value = "img", required = false) MultipartFile img,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.NOT_ACCEPTABLE);
    }
    if (audioWord == null) {}

    return new ResponseEntity<>(
        vocabularyService.createWord(vocabulary, audioWord, audiSentence, img), HttpStatus.OK);
  }

  @GetMapping({"/", ""})
  public ResponseEntity<?> getAllAudios() {
    return new ResponseEntity<List<VocabularyEntity>>(
        vocabularyService.getAllAudio(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public VocabularyEntity getAudioById(@PathVariable Long id) {
    return vocabularyService.getById(id);
  }

  //    @GetMapping(value = "/playAudio/{id}", produces = MediaType.ALL_VALUE)
  //    public void playAudio(@PathVariable Long id, HttpServletResponse response) {
  //        WordEntity audio = wordService.getById(id);
  //        InputStream resourse = null;
  //        try {
  //            resourse = fileService.getAudioFile(path, audio.getAudioName(), id);
  //            response.setContentType(MediaType.ALL_VALUE);
  //            StreamUtils.copy(resourse, response.getOutputStream());
  //        } catch (IOException ex) {
  //            throw new RuntimeException("Something went wrong", ex);
  //        }
  //    }

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
      @RequestBody VocabularyEntity vocabularyEntity, @RequestParam("audio") MultipartFile audio) {
    vocabularyService.update(vocabularyEntity, audio);
  }
}
