package com.example.service;

import com.example.dto.FileModel;
import com.example.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author BAO
 * 6/29/2023
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public FileModel uploadAudio(String path, MultipartFile multipartFile) throws IOException {
        FileModel fileModel = new FileModel();
        //bao gồm phần mở rộng của file
        String fileName = multipartFile.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();
        String finalName = randomId.concat(fileName).substring(fileName.indexOf("."));

        StringBuilder filePath = new StringBuilder();
        filePath.append(path);
        filePath.append(File.separator);
        filePath.append(finalName);

        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath.toString()));
        fileModel.setAudioFileName(fileName);
        return fileModel;
    }

    @Override
    public InputStream getAudioFile(String path, String fileName, Long id) {
        String fullPath = path + File.separator + fileName;
        try {
            return new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(false, "Audio not found " + e);
        }
    }
}
