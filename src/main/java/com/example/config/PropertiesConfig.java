package com.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author BAO 7/22/2023
 */
@Configuration
@PropertySource({
        "classpath:application-dev.properties"
})
@Getter
@Setter
@Component
public class PropertiesConfig {

    @Value("${dir.resource.audioWord}")
    private String pathAudioWord;

    @Value("${dir.resource.audioSentence}")
    private String pathAudioSentence;

    @Value("${dir.resource.imgWord}")
    private String pathImgWord;

    @Value("${dir.resource.imgCourse}")
    private String pathImgCourse;
    @Value("${dir.resource.imgTopic}")
    private String pathImgTopic;
}
