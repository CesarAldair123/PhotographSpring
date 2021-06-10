package com.example.Photograph.exception;

public class RefreshTokenNotFound extends RuntimeException{
    public RefreshTokenNotFound(String m) {
        super(m);
    }
}
