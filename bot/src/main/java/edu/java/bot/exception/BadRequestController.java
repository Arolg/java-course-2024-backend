package edu.java.bot.exception;

import edu.java.dto.response.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class BadRequestController {
    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        MethodArgumentTypeMismatchException.class})
    public ApiErrorResponse buildResponse(Exception e) {
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ApiErrorResponse globalExceptionHandler(Exception e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
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
