package com.example.Photograph.exception;

public class DuplicatedUsernameException extends RuntimeException{
    public DuplicatedUsernameException(String message){
        super(message);
    }
}
