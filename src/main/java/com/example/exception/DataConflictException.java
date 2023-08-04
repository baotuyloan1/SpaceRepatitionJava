package com.example.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ADMIN 8/3/2023
 */
@AllArgsConstructor
@Getter
public class DataConflictException extends RuntimeException {

  private final List<FieldsExistRes> fields;
  private final String message;

  public DataConflictException(String message, List<FieldsExistRes> fields) {
    this.message = message;
    this.fields = fields;
  }
}
