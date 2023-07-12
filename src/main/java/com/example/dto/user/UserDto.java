package com.example.dto.user;

import com.example.enums.RoleUser;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/12/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private long id;

    private RoleUser role;

    private String username;

    private String firstName;
    private String lastName;
    private int countWords;
    private String password;

@Email
    private String email;
    private String phone;
}
