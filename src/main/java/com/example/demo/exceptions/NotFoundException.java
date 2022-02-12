package com.example.demo.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@NoArgsConstructor
public class NotFoundException extends RuntimeException{

       public NotFoundException (String message) {
            super(message);
        }
    }
