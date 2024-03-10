package edu.java.scrapper.client;

import edu.java.dto.request.LinkUpdateRequest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotClient {
    private final WebClient webClient;
    private static final String UPDATES_ENDPOINT = "/updates";

    public BotClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public Mono<Void> sendUpdate(LinkUpdateRequest linkUpdate) {
        return webClient.post()
            .uri(UPDATES_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(linkUpdate)
            .retrieve()
            .bodyToMono(Void.class);
    }
}
