package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author BAO 7/19/2023
 */
@Getter
@Setter
public abstract class SignUpReponse {

  private long id;
  private String username;
  private String email;
}
