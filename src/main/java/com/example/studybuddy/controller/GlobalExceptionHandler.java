package com.example.studybuddy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,String>> handleRuntimeException(RuntimeException ex) {

        String message=ex.getMessage();

        if("GROUP_FULL".equals(message)){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error","GROUP_FULL"));
        }

        if("ALREADY_JOINED".equals(message)){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error","ALREADY_JOINED"));
        }

        if("GROUP_NOT_FOUND".equals(message)){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error","GROUP_NOT_FOUND"));
        }

        if("NOT_GROUP_CREATOR".equals(message)){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error","NOT_GROUP_CREATOR"));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error","INTERNAL_SERVER_ERROR"));
    }
}