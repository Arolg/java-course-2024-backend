package edu.java.scrapper.controller;

import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.ApiErrorResponse;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinksController {

    @Operation(summary = "Получить все отслеживаемые ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылки успешно получены",
                     content = @Content(
                         schema = @Schema(implementation = ListLinksResponse.class),
                         mediaType = MediaType.APPLICATION_JSON_VALUE
                     )),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         schema = @Schema(implementation = ApiErrorResponse.class),
                         mediaType = MediaType.APPLICATION_JSON_VALUE
                     ))
    })
    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader long tgChatId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(summary = "Добавить отслеживание ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылки успешно получены",
                     content = @Content(
                         schema = @Schema(implementation = LinkResponse.class),
                         mediaType = MediaType.APPLICATION_JSON_VALUE
                     )),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         schema = @Schema(implementation = ApiErrorResponse.class),
                         mediaType = MediaType.APPLICATION_JSON_VALUE
                     ))
    })
    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(@RequestHeader long tgChatId,
        @RequestBody AddLinkRequest addLinkRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(summary = "Убрать отслеживание ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылки успешно получены",
                     content = @Content(
                         schema = @Schema(implementation = LinkResponse.class),
                         mediaType = MediaType.APPLICATION_JSON_VALUE
                     )),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         schema = @Schema(implementation = ApiErrorResponse.class),
                         mediaType = MediaType.APPLICATION_JSON_VALUE
                     )),
        @ApiResponse(responseCode = "404",
                     description = "Ссылка не найдена",
                     content = @Content(
                         schema = @Schema(implementation = ApiErrorResponse.class),
                         mediaType = MediaType.APPLICATION_JSON_VALUE
                     ))
    })
    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> deleteLink(@RequestHeader long tgChatId,
        @RequestBody RemoveLinkRequest removeLinkRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
