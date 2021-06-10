package com.example.Photograph.handler;

import com.example.Photograph.dto.ExceptionDto;
import com.example.Photograph.exception.PostNotFound;
import com.example.Photograph.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class NotFoundHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionDto> handleNoHandlerFoundException(NoHandlerFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionDto.builder()
                    .error(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.value())
                    .build());
    }

    @ExceptionHandler(PostNotFound.class)
    public ResponseEntity<ExceptionDto> handlePostNotFound(PostNotFound e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionDto.builder()
                        .error(e.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .build());
    }
}
