package com.example.dto.fcm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ADMIN 7/27/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationRequest {
    private String deviceToken;
    private String deviceType;
}
