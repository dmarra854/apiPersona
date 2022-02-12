package com.example.demo.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
@NoArgsConstructor
public class InternalServerErrorException  extends RuntimeException {

    public InternalServerErrorException(String message) {
        super(message);
    }
}