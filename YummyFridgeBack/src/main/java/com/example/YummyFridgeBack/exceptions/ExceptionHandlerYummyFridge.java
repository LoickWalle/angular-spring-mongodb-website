package com.example.YummyFridgeBack.exceptions;

import com.example.YummyFridgeBack.DTO.ExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerYummyFridge {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionDTO> handleConflictException(ConflictException ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO(ex.getMessage(), ex.getStatus().value(), ex.getTime());

        return new ResponseEntity<>(exceptionDTO, ex.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleNotFoundException(ConflictException ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO(ex.getMessage(), ex.getStatus().value(), ex.getTime());

        return new ResponseEntity<>(exceptionDTO, ex.getStatus());
    }

    @ExceptionHandler(WrongParameterException.class)
    public ResponseEntity<ExceptionDTO> handleWrongParameterException(ConflictException ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO(ex.getMessage(), ex.getStatus().value(), ex.getTime());

        return new ResponseEntity<>(exceptionDTO, ex.getStatus());
    }

}
