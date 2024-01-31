package com.example.demo.Exceptions;

public class CatFoundException extends RuntimeException {
    public CatFoundException() {
        super("Sorry, that cat is found and cannot be second.");
    }
}
