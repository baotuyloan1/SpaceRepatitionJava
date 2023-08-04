package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/3/2023
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiRequestException extends RuntimeException {
  private Throwable throwable;
  private String message;


  public ApiRequestException(String message) {
    this.message = message;
  }
}
