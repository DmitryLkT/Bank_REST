package org.lukdt.bank_card_management.exception;

import org.lukdt.bank_card_management.exception.customException.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handlerUserAlreadyExists(UserAlreadyExistsException e) {
        return new ApiError(
                409,
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handlerUserNotFound(UserNotFoundException e) {
        return new ApiError(
                404,
                e.getMessage(),
                LocalDateTime.now()
        );
    }
}
