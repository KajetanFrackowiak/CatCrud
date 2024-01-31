package com.example.demo.exception.exceptionsClass;

public class CatNotExistException extends RuntimeException{
    public CatNotExistException() {super("Capybara is not exist yet.");}
}

