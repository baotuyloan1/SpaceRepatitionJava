package com.example.controller;

import com.example.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BAO 7/17/2023
 */
@RequestMapping("/api/v1/audio")
@RestController
public class AudioController {
  @Value("${dir.resource.audioWord}")
  private String pathAudioWord;

  @Value("${dir.resource.audioSentence}")
  private String pathAudioSentence;

    private final FileService fileService;

    public AudioController(FileService fileService) {
        this.fileService = fileService;
    }

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
}
