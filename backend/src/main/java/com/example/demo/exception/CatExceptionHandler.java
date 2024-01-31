//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo.exception;

import com.example.demo.exception.exceptionsClass.CatAgeIsToLowException;
import com.example.demo.exception.exceptionsClass.CatAlreadyExistException;
import com.example.demo.exception.exceptionsClass.CatNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CatExceptionHandler extends ResponseEntityExceptionHandler {
    public CatExceptionHandler() {
    }

    @ExceptionHandler({CatNotExistException.class})
    protected ResponseEntity<String>catIsNull(RuntimeException ex, WebRequest request) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CatAlreadyExistException.class})
    protected ResponseEntity<String> catExist(RuntimeException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler({CatAgeIsToLowException.class})
    protected ResponseEntity<String> catAgeIsToLow(RuntimeException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
