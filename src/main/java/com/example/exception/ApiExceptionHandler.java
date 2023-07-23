package com.example.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
  public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
    HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    ApiExceptionRes apiExceptionRes =
        new ApiExceptionRes(e.getMessage(), badRequest, ZonedDateTime.now(ZoneId.of("+7")));
    return new ResponseEntity<>(apiExceptionRes, badRequest);
  }

  @ExceptionHandler(value = {AuthenticationException.class})
  public ResponseEntity<ApiExceptionRes> handleAuthenticationException(
      AuthenticationException e) {
    HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
    ApiExceptionRes apiExceptionRes =
        new ApiExceptionRes(e.getMessage(), unauthorized, ZonedDateTime.now(ZoneId.of("+7")));
    return new ResponseEntity<>(apiExceptionRes, unauthorized);
  }
}
