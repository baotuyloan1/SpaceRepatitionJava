package com.example.exception;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ADMIN 8/2/2023
 */
@AllArgsConstructor
@Getter
@Setter
public class FieldsExistRes implements Serializable {
  private String field;
  private String reason;
}
