package edu.java.scrapper.exception;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionController {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "user already registered")
    public ErrorMessage chatAlreadyExistsException(Exception ex, WebRequest request) {
        return new ErrorMessage(ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "does not exist")
    public ErrorMessage chatDoesNotExistException(Exception ex, WebRequest request) {
        return new ErrorMessage(ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid args")
    public ErrorMessage illegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ErrorMessage(ex.getMessage());
    }
}
