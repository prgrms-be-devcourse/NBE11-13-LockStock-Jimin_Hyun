package com.example.lockstock.exception;


import com.example.lockstock.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<ErrorResponseDto> handleException(DuplicateUserIdException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDto(e.getMessage(), HttpStatus.CONFLICT.value()));
    } //ResponseEntity로 안 감싸면 200코드가 들어온다.

}
