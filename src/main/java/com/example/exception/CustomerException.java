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
@NoArgsConstructor
@AllArgsConstructor
public class CustomerException extends  RuntimeException {
    private Throwable throwable;
    private  String message;


}
