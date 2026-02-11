package org.example.exception;

import org.example.dto.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResult> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResult(ex.getMessage(), 404),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DuplicateRateException.class)
    public ResponseEntity<ErrorResult> handleDuplicate(DuplicateRateException ex) {
        return new ResponseEntity<>(
                new ErrorResult(ex.getMessage(), 409),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return new ResponseEntity<>(
                new ErrorResult(message, 400),
                HttpStatus.BAD_REQUEST
        );
    }
}
