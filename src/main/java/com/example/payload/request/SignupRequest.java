package com.example.payload.request;

import com.example.enums.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author BAO 7/12/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    private long id;
    private String username;
    private String firstName;
    private String lastname;
    private String password;
    private String email;
    private String phone;
    private Set<RoleUser> roles = new HashSet<>();
}
