package edu.java.bot.exception;

import edu.java.dto.response.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadRequestController {
    private ApiErrorResponse buildResponse(Exception e, String description) {
        return ApiErrorResponse.builder()
            .description(description)
            .code(HttpStatus.BAD_REQUEST.toString())
            .exceptionName(e.getClass().getName())
            .exceptionMessage(e.getMessage())
            .stacktrace(Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .toList())
            .build();
    }

}
