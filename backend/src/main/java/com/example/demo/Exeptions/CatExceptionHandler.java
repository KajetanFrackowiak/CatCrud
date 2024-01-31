package com.example.demo.Exeptions;

import com.example.demo.Cat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public class CatExceptionHandler {
    Cat repository;
    @ExceptionHandler(ArithmeticException.class)
    protected ResponseEntity<String> handleDivideByZero(RuntimeException ex, WebRequest request) {
        return ResponseEntity.badRequest().body("Sorry, no division by zero!");
    }
    @ExceptionHandler({CatNotFoundException.class})
    protected ResponseEntity<String> handleNotFoundException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.notFound().build();
    }

}
