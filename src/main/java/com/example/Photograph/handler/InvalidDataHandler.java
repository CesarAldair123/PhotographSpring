package com.example.Photograph.handler;

import com.example.Photograph.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidDataHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return generateResponse(e.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return generateResponse("Invalid Payload", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionDto> generateResponse(String message, HttpStatus status){
        return ResponseEntity.status(status)
                .body(ExceptionDto.builder()
                        .status(status.value())
                        .error(message)
                        .build());
    }

}
