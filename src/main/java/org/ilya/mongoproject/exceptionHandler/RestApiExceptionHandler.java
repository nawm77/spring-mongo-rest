package org.ilya.mongoproject.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class RestApiExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unexpectedException(Exception ex) {
        HttpStatus serverError = BAD_REQUEST;
        ApiException apiException = new ApiException(ex.getMessage(), serverError);
        log.warn(ex.getMessage());
        return new ResponseEntity<>(apiException, serverError);
    }
}
