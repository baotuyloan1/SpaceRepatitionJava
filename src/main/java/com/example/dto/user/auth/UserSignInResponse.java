package com.example.dto.user.auth;

import com.example.dto.SignInResponse;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/19/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInResponse extends SignInResponse {
  private long id;
  private String username;
  private String email;
  private List<String> roles;


}
