package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ADMIN 8/1/2023
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResApi<T> {
  private String message;
  private final boolean success = true;
  private T data;
}
