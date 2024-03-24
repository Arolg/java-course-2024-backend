package edu.java.scrapper.controller;

import edu.java.dto.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TgChatController {
    @Operation(summary = "Зарегистрировать чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Чат зарегистрирован",
                     content = @Content),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         schema = @Schema(implementation = ApiErrorResponse.class),
                         mediaType = MediaType.APPLICATION_JSON_VALUE
                     )),
    })
    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable("id") long id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(summary = "Удалить чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Чат успешно удален"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         schema = @Schema(implementation = ApiErrorResponse.class),
                         mediaType = MediaType.APPLICATION_JSON_VALUE
                     )),
        @ApiResponse(responseCode = "404",
                     description = "Чат не существует",
                     content = @Content(
                         schema = @Schema(implementation = ApiErrorResponse.class),
                         mediaType = MediaType.APPLICATION_JSON_VALUE
                     ))
    })
    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable("id") long id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
