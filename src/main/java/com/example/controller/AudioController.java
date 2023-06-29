package com.example.controller;

import com.example.entity.AudioEntity;
import com.example.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author BAO
 * 6/29/2023
 */
@RestController
@RequestMapping("/api")
public class AudioController {

    @Autowired
    private AudioService audioService;

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
}
