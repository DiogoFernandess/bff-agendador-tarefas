package com.diogo.bffagendadortarefas.controller;



import com.diogo.bffagendadortarefas.infrastructure.exceptions.ConflictException;
import com.diogo.bffagendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.diogo.bffagendadortarefas.infrastructure.exceptions.UnauthorizedException;
import com.diogo.bffagendadortarefas.infrastructure.exceptions.IllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHendler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handlerResourceNotFoundException(ResourceNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handlerConflictException(ConflictException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handlerUnauthorizedException(UnauthorizedException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handlerIllegalArgumentException(IllegalArgumentException exception){
        return new  ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
