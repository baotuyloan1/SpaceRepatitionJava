package com.example.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class MobileSignInRes {

    private long id;
    private List<String> roles;
    private String token;
}
