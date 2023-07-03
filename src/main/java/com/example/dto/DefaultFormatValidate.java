package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author BAO 7/3/2023
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultFormatValidate {
    List<ObjectError> errors;


}
