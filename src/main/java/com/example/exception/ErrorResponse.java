package com.example.exception;

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
public class ErrorResponse {

    // lưu trạng thái trả về
    private String status;

    private int statusCode;

//    sring của statuscode
    private String statusString;

    private String messageError;


}
