package com.example.ebookrepository.controller;

import com.example.ebookrepository.model.Status;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Status> catchErrors(DataIntegrityViolationException ex) {
        Status status = new Status(false, ex.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Status> catchNotFoundErrors(EmptyResultDataAccessException ex) {
        Status status = new Status(false, ex.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
    }

}
