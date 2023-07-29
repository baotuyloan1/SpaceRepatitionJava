package com.example.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserMobileSignInRes {

    private long id;
    private List<String> roles;
    private String token;
    private String username;
    private String firstName;
    private String lastName;
    private int countWords;
    private String email;
    private String phone;
}
