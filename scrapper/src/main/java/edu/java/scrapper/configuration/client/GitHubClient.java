package edu.java.scrapper.configuration.client;

import edu.java.scrapper.configuration.client.dto.response.GitHubClientResponse;
import edu.java.scrapper.configuration.exception.ErrorHandler;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClient {
    private final WebClient webClient;
    public final String defaultApiUrl = "https://api.github.com";

    public GitHubClient(String baseApiUrl) {
        var apiUrl = baseApiUrl == null || baseApiUrl.isEmpty()
            ? defaultApiUrl
            : baseApiUrl;
        this.webClient = WebClient.create(apiUrl);
    }


    public GitHubClientResponse fetchResponse(String owner, String repository) {
        var githubResponse = webClient.get()
            .uri("/repos/{owner}/{repo}", owner, repository)
            .exchangeToMono(response -> ErrorHandler.handleErrorResponse(response, GitHubClientResponse.class))
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
