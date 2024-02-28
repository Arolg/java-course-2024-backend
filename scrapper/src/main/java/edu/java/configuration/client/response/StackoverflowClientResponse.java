package edu.java.configuration.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.ArrayList;
@Setter
public class StackoverflowClientResponse implements HttpClientResponse {
    private OffsetDateTime lastUpdate;
    @JsonProperty("items")
    private ArrayList<Event> events;

    @Override
    public OffsetDateTime lastUpdate() {
        return lastUpdate;
    }
    public ArrayList<Event> events() {
        return events;
    }
    public record Event(
        @JsonProperty("creation_date")
        OffsetDateTime time,
        @JsonProperty("timeline_type")
        String type
    ) {
    }
}
