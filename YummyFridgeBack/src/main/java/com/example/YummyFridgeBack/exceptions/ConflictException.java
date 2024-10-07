package com.example.YummyFridgeBack.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ConflictException extends RuntimeException{
    private String message;
    private HttpStatus status;
    private LocalDateTime time;

    public ConflictException(String message, HttpStatus status, LocalDateTime time) {
        super(message);
        this.message = message;
        this.status = status;
        this.time = time;
    }
}
