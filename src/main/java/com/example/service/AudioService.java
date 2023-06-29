package com.example.service;

import com.example.entity.AudioEntity;

import java.util.List;

/**
 * @author BAO
 * 6/29/2023
 */
public interface AudioService {
    public AudioEntity createAudio(AudioEntity audioEntity);

    public AudioEntity getById(Long id);

    public AudioEntity updateAudio(AudioEntity audioEntity);
    public void deleteAudio(Long id);

    public List<AudioEntity> getAllAudio();
}
