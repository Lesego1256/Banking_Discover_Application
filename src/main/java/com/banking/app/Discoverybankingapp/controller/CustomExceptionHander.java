package com.banking.app.Discoverybankingapp.controller;


import com.banking.app.Discoverybankingapp.CustomException.ATMException;
import com.banking.app.Discoverybankingapp.CustomException.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHander {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> myMessage(CustomException c) {
        return new ResponseEntity<>("No accounts to display", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ATMException.class})
    public ResponseEntity<Object> myMessage(ATMException c) {
        return new ResponseEntity<>("ATM not registered or unfunded", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
