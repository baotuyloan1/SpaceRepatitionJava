package com.example.controller;

import com.example.service.notifications.MessageService;
import lombok.AllArgsConstructor;
import nl.martijndwars.webpush.Subscription;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ADMIN 7/28/2023
 */
@RestController
@AllArgsConstructor
public class MessageController {

  private final MessageService messageService;

  @GetMapping
  public String getPubicKey() {
    return messageService.getPublicKey();
  }

  @PostMapping
  public void subscribe(Subscription subscription) {
    messageService.subscribe(subscription);
  }

  @PutMapping
  public void unsubscribe(String endpoint) {
    messageService.unsubscribe(endpoint);
  }
}
