package com.example.payload.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BAO 7/12/2023
 */
@Getter
@Setter
public class LoginRequest {

    private String username;
    private String password;
}
