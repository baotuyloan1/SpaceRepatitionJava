package com.example.controller;

import com.example.dto.FileModel;
import com.example.entity.WordEntity;
import com.example.service.WordService;
import com.example.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author BAO
 * 6/29/2023
 */
@RestController
@RequestMapping("/api/words")
@CrossOrigin(origins = "http://localhost:3000")
public class WordController {

    @Value("${project.audio}")
    private String path;

    private final WordService wordService;
    private final FileService fileService;

    public WordController(WordService wordService, FileService fileService) {
        this.wordService = wordService;
        this.fileService = fileService;
    }


//    @PostMapping({"/",""})
//    public ResponseEntity<?> saveWord(@RequestBody WordEntity wordEntity, Mu) {
//        return new ResponseEntity<WordEntity>(wordService.createWord(wordEntity), HttpStatus.OK);
//    }


    @GetMapping({"/",""})
    public ResponseEntity<?> getAllAudios() {
        return new ResponseEntity<List<WordEntity>>(wordService.getAllAudio(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public WordEntity getAudioById(@PathVariable Long id) {
        return wordService.getById(id);
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
//    public WordEntity uploadingAudio(@RequestParam("audio") MultipartFile audio, @PathVariable Long id) throws IOException {
//        WordEntity wordEntity = wordService.getById(id);
//        FileModel fileModel = fileService.uploadAudio(path, audio);
//        wordEntity.setAudioName(fileModel.getAudioFileName());
//        return wordService.update(wordEntity, audio);
//    }

    @PutMapping(value = {"","/"})
    public void updateWord(@RequestBody WordEntity wordEntity, @RequestParam("audio") MultipartFile audio){
        wordService.update(wordEntity, audio);
    }
}
