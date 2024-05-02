package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.model.ErrorsResponse;
import br.com.fiap.techchallenge.model.dto.ClienteDTO;
import br.com.fiap.techchallenge.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Cadastrar Cliente", description = "Esta operação consiste em criar um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request" ,  content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class)) }) })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE )
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Void> cadastrarCliente(@Valid @RequestBody ClienteDTO clienteRequest) {
        boolean registryOk = true;
        if(registryOk) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
