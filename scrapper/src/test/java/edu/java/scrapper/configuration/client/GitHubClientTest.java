package edu.java.scrapper.configuration.client;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import java.time.OffsetDateTime;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.dto.response.GitHubClientResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GitHubClientTest {
    @RegisterExtension
    private static WireMockExtension wireMockExtension =
        WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

    private GitHubClient gitHubClient;
    @BeforeEach
    public void setup() {
        gitHubClient = new GitHubClient(wireMockExtension.baseUrl());
    }

    private void configStubGitHub() {
        wireMockExtension.stubFor(get(urlEqualTo("/repos/user/test"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("""
                                {
                                    "id": 1,
                                    "name": "test",
                                    "created_at": "2023-09-13T21:17:36Z",
                                    "updated_at": "2024-02-18T10:28:37Z",
                                    "pushed_at": "2024-01-31T22:21:31Z"
                                }
                                """)));
    }

    @Test
    public void gitHubTest() {
        configStubGitHub();
        GitHubClientResponse response = gitHubClient.fetchResponse("user", "test");

        assertEquals((OffsetDateTime.parse("2024-02-18T10:28:37Z")), response.lastUpdate());
        assertEquals(OffsetDateTime.parse("2024-01-31T22:21:31Z"), response.pushUpdate());
    }

}
