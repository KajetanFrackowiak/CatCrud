package com.example.demo.Exeptions;

public class CatNotFoundException extends RuntimeException{
    public CatNotFoundException() {super("Sorry, that cat is not found.");}
}

