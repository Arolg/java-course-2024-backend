package edu.java.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record LinkUpdateRequest(
    @NotNull
    Long id,
    @NotBlank
    String url,
    @NotNull
    String description,
    @NotNull
    List<Long> tgChatIds

) {

}
