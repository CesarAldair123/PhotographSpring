package com.example.Photograph.handler;

import com.example.Photograph.dto.ExceptionDto;
import com.example.Photograph.exception.DuplicatedEmailException;
import com.example.Photograph.exception.DuplicatedUsernameException;
import com.example.Photograph.exception.RefreshTokenNotFound;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationHandler {
    @ExceptionHandler(DuplicatedUsernameException.class)
    public ResponseEntity<ExceptionDto> handleDuplicatedUsernameException(DuplicatedUsernameException e){
        return generateResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<ExceptionDto> handleDuplicatedEmailException(DuplicatedEmailException e){
        return generateResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RefreshTokenNotFound.class)
    public ResponseEntity<ExceptionDto> handleRefreshTokenNotFound(RefreshTokenNotFound e){
        return generateResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionDto> handleExpiredJwtException(ExpiredJwtException e){
        return generateResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ExceptionDto> handleSignatureException(SignatureException e){
        return generateResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionDto> handleAuthenticationException(AuthenticationException e){
        return generateResponse(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<ExceptionDto> generateResponse(String message, HttpStatus status){
        return ResponseEntity.status(status)
                .body(ExceptionDto.builder()
                        .status(status.value())
                        .error(message)
                        .build());
    }
}
