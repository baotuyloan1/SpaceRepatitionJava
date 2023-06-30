package com.example.service;

import com.example.dto.FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author BAO
 * 6/29/2023
 */
public interface FileService {

    FileModel uploadAudio(String path, MultipartFile multipartFile) throws IOException;

    InputStream getAudioFile(String path, String fileName, Long id);


}
