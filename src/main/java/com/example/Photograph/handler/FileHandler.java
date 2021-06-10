package com.example.Photograph.handler;

import com.example.Photograph.dto.ExceptionDto;
import com.example.Photograph.exception.FileNotFoundException;
import com.example.Photograph.exception.FileStorageException;
import com.example.Photograph.exception.InvalidContentTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileHandler {
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleFileNotFoundException(FileNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ExceptionDto> handleFileStorageException(FileStorageException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(InvalidContentTypeException.class)
    public ResponseEntity<ExceptionDto> handleInvalidContentTypeException(InvalidContentTypeException e){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ExceptionDto(e.getMessage(), HttpStatus.NOT_ACCEPTABLE.value()));
    }
}
