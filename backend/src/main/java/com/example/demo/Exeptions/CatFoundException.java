package com.example.demo.Exeptions;

public class CatFoundException extends RuntimeException {
    public CatFoundException() {
        super("Sorry, that cat is found and cannot be second.");
    }
}
