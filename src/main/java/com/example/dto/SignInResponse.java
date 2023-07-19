package com.example.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/19/2023
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class SignInResponse {

  private long id;
  private String username;
  private String email;
  private List<String> roles;
}
