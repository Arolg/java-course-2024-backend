package edu.java.scrapper.exception;

import edu.java.dto.response.ApiErrorResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.util.Arrays;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ChatAlreadyRegisteredException.class)
    public ApiErrorResponse chatAlreadyExistsException(Exception e) {
        return handleException(e, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(NotFoundException.class)
    public ApiErrorResponse chatDoesNotExistException(Exception e) {
        return handleException(e, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        MethodArgumentTypeMismatchException.class,
        BadRequestException.class})
    public ApiErrorResponse illegalArgumentException(Exception e) {
        return handleException(e, HttpStatus.BAD_REQUEST);

    }

    private ApiErrorResponse handleException(Exception e, HttpStatus httpStatus) {
        return ApiErrorResponse.builder()
            .description(httpStatus.getReasonPhrase())
            .code(httpStatus.toString())
            .exceptionName(e.getClass().getName())
            .exceptionMessage(e.getMessage())
            .stacktrace(Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .toList())
            .build();
    }
}
