package com.example.service;

import com.example.dto.FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author BAO
 * 6/29/2023
 */
public class FileServiceImpl implements FileService {
    @Override
    public FileModel uploadAudio(String path, MultipartFile multipartFile) throws IOException {
//        FileModel fileModel = new FileModel("xcsad")
        return null;
    }

    @Override
    public InputStream getAudioFile(String path, String fileName, int id) throws FileNotFoundException {
        return null;
    }
}
