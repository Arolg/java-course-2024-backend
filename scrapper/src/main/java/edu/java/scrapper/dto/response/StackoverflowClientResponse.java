package edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import lombok.Setter;

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
        @JsonProperty("last_activity_date")
        OffsetDateTime time,
        @JsonProperty("question_id")
        long id,
        @JsonProperty("answer_count")
        long answerCount

    ) {
    }
}
