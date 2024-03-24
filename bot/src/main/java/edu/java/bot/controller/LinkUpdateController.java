package edu.java.bot.controller;

import edu.java.dto.request.LinkUpdateRequest;
import edu.java.dto.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
public class LinkUpdateController {
    @Operation(summary = "Отправить обновление")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Обновление обработано",
                     content = @Content),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         schema = @Schema(implementation = ApiErrorResponse.class),
                         mediaType = MediaType.APPLICATION_JSON_VALUE
                     )),
    })
    @PostMapping
    public ResponseEntity<?> handleUpdate(@RequestBody @Valid LinkUpdateRequest linkUpdate) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
