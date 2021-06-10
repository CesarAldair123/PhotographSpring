package com.example.Photograph.exception;

public class InvalidVerificationToken extends RuntimeException{
    public InvalidVerificationToken(String message){
        super(message);
    }
}
