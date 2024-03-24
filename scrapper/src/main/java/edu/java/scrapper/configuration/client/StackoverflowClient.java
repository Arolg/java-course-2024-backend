package edu.java.scrapper.configuration.client;

import edu.java.scrapper.configuration.client.dto.response.StackoverflowClientResponse;
import edu.java.scrapper.configuration.exception.ErrorHandler;
import org.springframework.web.reactive.function.client.WebClient;

public class StackoverflowClient {
    private final WebClient webClient;
    public final String defaultApiUrl = "https://api.stackexchange.com/2.3";

    public StackoverflowClient(String baseApiUrl) {
        var apiUrl = baseApiUrl == null || baseApiUrl.isEmpty()
            ? defaultApiUrl
            : baseApiUrl;
        this.webClient = WebClient.create(apiUrl);
    }


    public StackoverflowClientResponse fetchResponse(String questionId) {
        var stackoverflowResponse = webClient.get()
            .uri(String.format(
                "/questions/%s?site=stackoverflow", questionId
                )
            )
            .exchangeToMono(response -> ErrorHandler.handleErrorResponse(response, StackoverflowClientResponse.class))
            .block();
        if (stackoverflowResponse != null
            && stackoverflowResponse.events() != null
            && !stackoverflowResponse.events().isEmpty()
        ) {
            boolean isAllEventsHaveData = stackoverflowResponse.events().stream()
                .allMatch(event -> event.time() != null && event.id() != 0L && event.answerCount() != 0L);
            if (isAllEventsHaveData) {
                stackoverflowResponse.setLastUpdate(stackoverflowResponse.events().getFirst().time());
                return stackoverflowResponse;
            }
        }
        throw new RuntimeException(
            "Error occurred during fetchResponse in StackoverflowClient! Message: empty response/data"
        );
    }
}
