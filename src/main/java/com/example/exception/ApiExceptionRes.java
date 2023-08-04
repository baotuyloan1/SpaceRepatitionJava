package com.example.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiExceptionRes<T> {
  private List<T> errors;
  private String message;
  private final boolean success = false;
}
