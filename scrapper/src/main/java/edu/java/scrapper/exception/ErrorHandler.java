package edu.java.scrapper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

public class ErrorHandler {

    private ErrorHandler() {}

    public static <T> Mono<T> handleErrorResponse(ClientResponse response, Class<T> responseType) {
        HttpStatus status = (HttpStatus) response.statusCode();
        if (status.is2xxSuccessful()) {
            return response.bodyToMono(responseType);
        } else if (status.is4xxClientError()) {
            return Mono.error(new RuntimeException(
                "Client error during fetchResponse in GithubClient! Message: "
                    + status.value() + " "
                    + status.getReasonPhrase()));
        } else if (status.is5xxServerError()) {
            return Mono.error(new RuntimeException(
                "Server error during fetchResponse in GithubClient! Message: "
                    + status.value() + " "
                    + status.getReasonPhrase()));
        } else {
            return Mono.error(new RuntimeException(
                "Unexpected status code during fetchResponse in GithubClient! Message: "
                    + status.value() + " "
                    + status.getReasonPhrase()));
        }
    }
}
