package com.example.utils;

import com.example.config.PropertiesConfig;
import com.example.exception.ApiRequestException;
import com.example.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author BAO 7/5/2023
 */
@Component
@AllArgsConstructor
public class FileUtils {

  private final PropertiesConfig env;
  private final FileService fileService;

  public String saveFile(MultipartFile multipartFile, long id, String path, String suffix)
      throws IOException {
    String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
    StringBuilder absolutePath = new StringBuilder();
    StringBuilder fileName = new StringBuilder();
    fileName.append(id).append("_").append(suffix).append(".").append(extension);
    absolutePath.append(path).append(File.separator).append(fileName);
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    try {
      Files.copy(multipartFile.getInputStream(), Paths.get(absolutePath.toString()));
    } catch (IOException e) {
      throw new IOException(e);
    }
    return fileName.toString();
  }

  public void playAudio(String fileName, HttpServletResponse response) {
    String type = fileName.substring((fileName.indexOf("_") + 1), fileName.indexOf("."));
    String path = null;
    if (type.equals("word")) {
      path = env.getPathAudioWord();
    } else if (type.equals("sentence")) {
      path = env.getPathAudioSentence();
    }
    InputStream resource = null;
    try {
      resource = fileService.getAudioFile(path, fileName);
      response.setContentType(MediaType.ALL_VALUE);
      StreamUtils.copy(resource, response.getOutputStream());
    } catch (IOException ex) {
      throw new ApiRequestException(ex, "Something went wrong");
    }
  }
}
