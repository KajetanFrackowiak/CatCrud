package com.example.demo.Exceptions;

public class CatNotFoundException extends RuntimeException{
    public CatNotFoundException() {super("Sorry, that cat is not found.");}
}

