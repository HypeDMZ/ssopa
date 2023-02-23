package com.example.demo.Exception.Auth;

public class alreadyRegisteredException extends RuntimeException{
    public alreadyRegisteredException() {
    }
    public  alreadyRegisteredException(String message) {
        super(message);
    }
}
