package com.example.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author BAO 7/5/2023
 */

@Component
public class FileUtils {

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
}
