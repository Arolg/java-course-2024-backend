package edu.java.configuration.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record  GitHubClientResponse(
    Long id,
    String name,
    @JsonProperty("updated_at")
    OffsetDateTime lastUpdate,
    @JsonProperty("pushed_at")
    OffsetDateTime pushUpdate
) implements HttpClientResponse {
    @Override
    public String toString() {
        return String.format(
            "GithubClientResponse{ id=%d, name=%s, lastUpdate=%s, pushUpdate=%s }%n",
            id, name, lastUpdate.toString(), pushUpdate.toString()
        );
    }

}
