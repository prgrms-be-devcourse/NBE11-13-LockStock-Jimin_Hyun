package com.example.lockstock.exception;


import com.example.lockstock.dto.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<ErrorResponseDto> handleException(DuplicateUserIdException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDto(HttpStatus.CONFLICT.value(), e.getMessage()));
    } //ResponseEntity로 안 감싸면 200코드가 들어온다.

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleException(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    } //ResponseEntity로 안 감싸면 200코드가 들어온다.

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    } //ResponseEntity로 안 감싸면 200코드가 들어온다.

    // * 최후의 보루 핸들러 : 위에서 처리하지 못한 "모든 예외"를 잡는다.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> exception(Exception e) {
        log.warn("500 응답(예상치 못한 예외 발생)", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류가 발생했습니다.")
                );
    }
}
