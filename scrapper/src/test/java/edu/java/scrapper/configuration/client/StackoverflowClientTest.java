package edu.java.scrapper.configuration.client;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import edu.java.scrapper.client.StackoverflowClient;
import edu.java.scrapper.dto.response.StackoverflowClientResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class StackoverflowClientTest {
    @RegisterExtension
    private static WireMockExtension wireMockExtension =
        WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

    private StackoverflowClient stackOverflowClient;
    @BeforeEach
    public void setup() {
        stackOverflowClient = new StackoverflowClient(wireMockExtension.baseUrl());
    }

    @Test
    public void stackOverflowTest() {
        wireMockExtension.stubFor(get("/questions/59073648?site=stackoverflow")
            .willReturn(okJson("""
                {
                     "items": [
                         {
                             "tags": [
                                 "spring",
                                 "spring-boot"
                             ],
                             "owner": {
                                 "account_id": 955045,
                                 "reputation": 1578,
                                 "user_id": 980515,
                                 "user_type": "registered",
                                 "accept_rate": 40,
                                 "profile_image": "https://www.gravatar.com/avatar/0d3e9bf3a67f85b77a6cf587b07f798f?s=256&d=identicon&r=PG",
                                 "display_name": "Fabry",
                                 "link": "https://stackoverflow.com/users/980515/fabry"
                             },
                             "is_answered": true,
                             "view_count": 49943,
                             "accepted_answer_id": 59079239,
                             "answer_count": 1,
                             "score": 6,
                             "last_activity_date": 1574893384,
                             "creation_date": 1574868891,
                             "last_edit_date": 1574871009,
                             "question_id": 59073648,
                             "content_license": "CC BY-SA 4.0",
                             "link": "https://stackoverflow.com/questions/59073648/springboot-failed-to-bind-properties-under-app",
                             "title": "SpringBoot Failed to bind properties under app"
                         }
                     ],
                     "has_more": false,
                     "quota_max": 300,
                     "quota_remaining": 295
                 }""")));
        StackoverflowClientResponse response = stackOverflowClient.fetchResponse("59073648");

        assertEquals(59073648, response.events().getFirst().id());
        assertEquals(1, response.events().getFirst().answerCount());
        assertEquals(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1574893384), ZoneOffset.UTC), response.lastUpdate());
    }
}
