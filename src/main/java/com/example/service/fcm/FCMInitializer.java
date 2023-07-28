package com.example.service.fcm;

import com.example.config.PropertiesConfig;
import com.example.exception.ApiRequestException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * @author ADMIN 7/27/2023
 */
@Service
@AllArgsConstructor
public class FCMInitializer {

  private PropertiesConfig env;

  @PostConstruct
  public void initialize() {
    try {
      GoogleCredentials googleCredentials =
          GoogleCredentials.fromStream(
              new ClassPathResource(env.getFilebaseConfigPath()).getInputStream());
      FirebaseOptions firebaseOptions =
          FirebaseOptions.builder().setCredentials(googleCredentials).build();
      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(firebaseOptions);
      }
      FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
      FirebaseMessaging.getInstance(app);
    } catch (IOException e) {
      throw new ApiRequestException(e, e.getMessage());
    }
  }
}
