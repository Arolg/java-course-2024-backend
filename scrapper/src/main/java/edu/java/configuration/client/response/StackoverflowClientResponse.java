package edu.java.configuration.client.response;

import java.time.OffsetDateTime;

public record StackoverflowClientResponse(


) implements HttpClientResponse {
    @Override
    public OffsetDateTime lastUpdate() {
        return null;
    }
}
