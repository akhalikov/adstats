package com.akhalikov.adstats.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(value = {DeliveryNotFoundException.class, ClickNotFoundException.class})
  public void handleNotFound() {
  }
}
