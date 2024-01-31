package com.example.demo.exception.exceptionsClass;

public class CatAlreadyExistException extends RuntimeException {
    public CatAlreadyExistException() {
        super("This Cat Already Exist");
    }
}
