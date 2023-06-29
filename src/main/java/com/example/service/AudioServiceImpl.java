package com.example.service;

import com.example.entity.AudioEntity;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AudioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author BAO
 * 6/29/2023
 */
@Service
public class AudioServiceImpl implements AudioService {

    private final AudioRepository audioRepository;

    public AudioServiceImpl(AudioRepository audioRepository) {
        this.audioRepository = audioRepository;
    }

    @Override
    public AudioEntity createAudio(AudioEntity audioEntity) {
        if (audioEntity.getTitle().isEmpty()) {
            throw new ResourceNotFoundException(false, "audio title can not be null or empty");
        }
        try {
            audioEntity.setAddedDate(new Date());
            return audioRepository.save(audioEntity);
        } catch (Exception e) {
            throw new ResourceNotFoundException(false, "something went wrong");
        }

    }

    @Override
    public AudioEntity getById(Long id) {
        return audioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(false, "Audio not found"));
    }

    @Transactional
    @Override
    public AudioEntity updateAudio(AudioEntity audioEntity) {
        return audioRepository.save(audioEntity);

    }

    @Override
    public void deleteAudio(Long id) {

    }

    @Override
    public List<AudioEntity> getAllAudio() {
        return audioRepository.findAll();
    }
}
