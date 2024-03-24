package edu.java.bot.client;


import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperClient {
    private final WebClient webClient;

    private static final String TG_CHAT_ENDPOINT = "/tg-chat/{id}";
    private static final String TG_CHAT_ID_HEADER = "Tg-Chat-Id";
    private static final String LINKS_ENDPOINT = "/links";

    public ScrapperClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public Mono<Void> registerChat(long id) {
        return webClient.post()
            .uri(TG_CHAT_ENDPOINT, id)
            .retrieve()
            .bodyToMono(Void.class);
    }


    public Mono<Void> deleteChat(long id) {
        return webClient.delete()
            .uri(TG_CHAT_ENDPOINT, id)
            .retrieve()
            .bodyToMono(Void.class);
    }


    public Mono<ListLinksResponse> getAllLinks(long tgChatId) {
        return webClient.get()
            .uri(LINKS_ENDPOINT)
            .header(TG_CHAT_ID_HEADER, String.valueOf(tgChatId))
            .retrieve()
            .bodyToMono(ListLinksResponse.class);
    }


    public Mono<LinkResponse> addLink(long tgChatId, AddLinkRequest dto) {
        return webClient.post()
            .uri(LINKS_ENDPOINT)
            .header(TG_CHAT_ID_HEADER, String.valueOf(tgChatId))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(dto)
            .retrieve()
            .bodyToMono(LinkResponse.class);
    }


    public Mono<LinkResponse> deleteLink(long tgChatId, RemoveLinkRequest dto) {
        return webClient.method(HttpMethod.DELETE)
            .uri(LINKS_ENDPOINT)
            .header(TG_CHAT_ID_HEADER, String.valueOf(tgChatId))
            .bodyValue(dto)
            .retrieve()
            .bodyToMono(LinkResponse.class);
    }
}
