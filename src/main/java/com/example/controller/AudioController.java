package com.example.controller;

import com.example.dto.FileModel;
import com.example.entity.AudioEntity;
import com.example.service.AudioService;
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
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AudioController {

    @Value("${project.audio}")
    private String path;

    private final AudioService audioService;
    private final FileService fileService;

    public AudioController(AudioService audioService, FileService fileService) {
        this.audioService = audioService;
        this.fileService = fileService;
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveAudio(@RequestBody AudioEntity audioEntity) {
        return new ResponseEntity<AudioEntity>(audioService.createAudio(audioEntity), HttpStatus.OK);
    }


    @GetMapping("/allAudios")
    public ResponseEntity<?> getAllAudios() {
        return new ResponseEntity<List<AudioEntity>>(audioService.getAllAudio(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public AudioEntity getAudioById(@PathVariable Long id) {
        return audioService.getById(id);
    }

    // Api for uploading audio after posting titles and other audio
    @PostMapping("/post/{id}")
    public AudioEntity uploadingAudio(@RequestParam("audio") MultipartFile audio, @PathVariable Long id) throws IOException {
        AudioEntity audioEntity = audioService.getById(id);
        FileModel fileModel = fileService.uploadAudio(path, audio);
        audioEntity.setAudioName(fileModel.getAudioFileName());
        return audioService.updateAudio(audioEntity);
    }

    @GetMapping(value = "/playAudio/{id}", produces = MediaType.ALL_VALUE)
    public void playAudio(@PathVariable Long id, HttpServletResponse response) {
        AudioEntity audio = audioService.getById(id);
        InputStream resourse = null;
        try {
            resourse = fileService.getAudioFile(path, audio.getAudioName(), id);
            response.setContentType(MediaType.ALL_VALUE);
            StreamUtils.copy(resourse, response.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException("Something went wrong", ex);
        }
    }
}
