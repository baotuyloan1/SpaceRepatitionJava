package com.example.service;

import com.example.entity.WordEntity;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.WordRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * @author BAO
 * 6/29/2023
 */
@Service
public class WordServiceImpl implements WordService {

    private final WordRepository audioRepository;

    private final String dirAudioWord = "D:/Self-Study/SpringBoot/LearnLanguageProject/audio/words";
    private final String dirAudioSentence = "D:/Self-Study/SpringBoot/LearnLanguageProject/audio/sentences";

    public WordServiceImpl(WordRepository audioRepository) {
        this.audioRepository = audioRepository;
    }

    @Transactional
    @Override
    public WordEntity createWord(WordEntity wordEntity, MultipartFile audioWord, MultipartFile audioSentence) {
        if (wordEntity.getWord() == null || wordEntity.getWord().isEmpty()) {
            throw new ResourceNotFoundException(false, "Word can not be null or empty");
        }
        try {
            wordEntity.setAddedDate(new Date());
            WordEntity savedWord = audioRepository.save(wordEntity);
            // saveAudioWord, có nhiều extentions file mp3 khác nhau
            if (!audioWord.isEmpty()) {
                String audioWordFileName = saveAudio(audioWord, savedWord.getId(), dirAudioWord, "word");
                savedWord.setAudioWord(audioWordFileName);
            } else {
                throw new IllegalArgumentException();
            }
            // saveAudioSentence
            if (!audioSentence.isEmpty()) {
                String audioSentenceFileName = saveAudio(audioSentence, savedWord.getId(), dirAudioSentence, "sentence");
                savedWord.setAudioSentence(audioSentenceFileName);
            } else {
                throw new IllegalArgumentException();
            }
            return audioRepository.save(savedWord);
        } catch (Exception e) {
            throw new ResourceNotFoundException(false, "something went wrong");
        }

    }

    private String saveAudio(MultipartFile audio, long id, String path, String audioType) throws IOException {
        String extension = FilenameUtils.getExtension(audio.getOriginalFilename());
        StringBuilder absolutePath = new StringBuilder();
        StringBuilder fileName = new StringBuilder();
        fileName.append(id).append("_").append(audioType);
        absolutePath.append(path).append(File.separator).append(fileName.toString()).append(extension);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            Files.copy(audio.getInputStream(), Paths.get(absolutePath.toString()));
        } catch (IOException e) {
            throw new IOException(e);
        }

        return fileName.toString();
    }

    @Override
    public WordEntity getById(Long id) {
        return audioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(false, "Audio not found"));
    }

    @Transactional
    @Override
    public WordEntity update(WordEntity wordEntity, MultipartFile audio) {
        return audioRepository.save(wordEntity);

    }

    @Override
    public void deleteAudio(Long id) {

    }

    @Override
    public List<WordEntity> getAllAudio() {
        return audioRepository.findAll();
    }
}
