package com.example.dto;

import com.example.enums.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * @author BAO 7/19/2023
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class SignUpRequest {
    private String username;
    private String firstName;
    private String lastname;
    private String password;
    private String email;
    private String phone;
    private Set<RoleUser> roles;
}
