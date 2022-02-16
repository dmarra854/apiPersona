package com.example.demo.exceptions;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ApiExceptionHandler {

     @ResponseStatus(HttpStatus.BAD_REQUEST)
     @ExceptionHandler(MethodArgumentNotValidException.class)
     public Map<String, String> handleValidationExceptions(
             MethodArgumentNotValidException ex) {
          Map<String, String> errors = new HashMap<>();
          ex.getBindingResult().getAllErrors().forEach((error) -> {
               String fieldName = ((FieldError) error).getField();
               String errorMessage = error.getDefaultMessage();
               errors.put(fieldName, errorMessage);
          });
          return errors;
     }

     @ExceptionHandler(BadRequestException.class)
     public ResponseEntity<?> badRequestException(BadRequestException e) {
          HttpStatus badRequest = HttpStatus.BAD_REQUEST;
          return ResponseEntity.status(badRequest).body(e.getMessage());
     }

     @ExceptionHandler(value = {NotFoundException.class})
     public ResponseEntity<?> notFoundException(Exception e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
     }

}
