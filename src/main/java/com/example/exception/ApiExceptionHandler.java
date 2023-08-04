package com.example.exception;

import com.example.dto.BaseResApi;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author BAO 7/20/2023
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {SQLException.class})
    protected ResponseEntity<Object> handleConflict(
        RuntimeException exception, WebRequest webRequest) {
      String bodyOfResponse = exception.getMessage();
    return handleExceptionInternal(
        exception, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }

  @ExceptionHandler(value = {ApiRequestException.class})
  public ResponseEntity<BaseResApi> handleApiRequestException(ApiRequestException e) {
    HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    ApiExceptionRes apiExceptionRes = null;
    BaseResApi res = null;
    res.setMessage(e.getMessage());
    res.setData(apiExceptionRes);
    return new ResponseEntity<>(res, badRequest);
  }

  @ExceptionHandler(value = {AuthenticationException.class})
  public ResponseEntity<ApiExceptionRes<ErrorResponse>> handleAuthenticationException(
      AuthenticationException e) {
    ArrayList<ErrorResponse> errors = new ArrayList<>();
    errors.add(new ErrorResponse(e.getMessage(),"Login required"));
    ApiExceptionRes<ErrorResponse> res = new ApiExceptionRes<>(errors, "Yêu cầu login");
    return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = {DataConflictException.class})
  public ResponseEntity<ApiExceptionRes<FieldsExistRes>> handleConflictException(
      DataConflictException e) {
    ApiExceptionRes<FieldsExistRes> res = new ApiExceptionRes<>(e.getFields(), e.getMessage());
    return new ResponseEntity<>(res, HttpStatus.CONFLICT);
  }
}
