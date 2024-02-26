package edu.java.configuration.client;

import edu.java.configuration.client.response.GitHubClientResponse;
import edu.java.configuration.client.response.HttpClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

public class GitHubClient implements HttpClient {
    private final WebClient webClient;
    public final String defaultApiUrl = "https://api.github.com";

    public GitHubClient(String baseApiUrl) {
        var apiUrl = baseApiUrl == null || baseApiUrl.isEmpty()
            ? defaultApiUrl
            : baseApiUrl;
        this.webClient = WebClient.create(apiUrl);
    }

    @Override
    public GitHubClientResponse fetchResponse(String repository) {
        var githubResponse = webClient.get()
            .uri(String.format("/repos%s", repository))
            .exchangeToMono(response -> {
                HttpStatus status = (HttpStatus) response.statusCode();
                if (status.is2xxSuccessful()) {
                    return response.bodyToMono(GitHubClientResponse.class);
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
            })
            .block();
        if (githubResponse == null
            || githubResponse.lastUpdate() == null
            || githubResponse.pushUpdate() == null
            || githubResponse.name() == null
            || githubResponse.id() == null) {
            throw new RuntimeException(
                "Error occurred during fetchResponse in GithubClient! Message: empty response/data"
            );
        } else {
            return githubResponse;
        }
    }
}
