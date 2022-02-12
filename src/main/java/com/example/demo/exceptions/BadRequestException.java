package com.example.demo.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }
}

