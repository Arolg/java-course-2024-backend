package edu.java.configuration.client;

import edu.java.configuration.client.response.HttpClientResponse;

public interface HttpClient {
    HttpClientResponse fetchResponse(String endpoint);
}
