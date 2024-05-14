package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.GetClienteAdapter;
import br.com.fiap.techchallenge.adapters.PatchClienteAdapter;
import br.com.fiap.techchallenge.domain.model.ErrorsResponse;
import br.com.fiap.techchallenge.domain.model.mapper.ClienteMapper;
import br.com.fiap.techchallenge.domain.usecases.GetClienteUseCase;
import br.com.fiap.techchallenge.domain.usecases.PatchClienteUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import br.com.fiap.techchallenge.ports.PatchClienteOutboundPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final GetClienteAdapter getClienteAdapter;
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Operation(summary = "Cadastrar Cliente", description = "Esta operação consiste em criar um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))})})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody ClienteDTO clienteRequest) {
        boolean registryOk = true;
        if (registryOk) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(summary = "Listar Clientes", description = "Está operação consiste em retornar todos os clientes cadastrados paginados por página e tamanho")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "204", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            })
    })
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<ClienteDTO>> listarTodosClientes(@RequestParam Integer page,
                                                                @RequestParam Integer size,
                                                                @RequestParam(required = false) String email,
                                                                @RequestParam(required = false) String cpf
    ) {
        GetClienteUseCase getClienteUseCase = new GetClienteUseCase(getClienteAdapter);
        List<ClienteDTO> clientes = getClienteUseCase.listarClientes(page, size, email, cpf);
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }

    @Operation(summary = "Atualizar Clientes", description = "Está operação consiste em atualizar os clientes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))
                    }
            ),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))
            })
    })
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<ClienteDTO> atualizarClientes(@RequestBody ClienteDTO clienteDTO
    ) {
        log.info("Atualizando cliente.");
        PatchClienteOutboundPort patchClienteAdapter = new PatchClienteAdapter(clienteRepository, clienteMapper);
        PatchClienteUseCase patchClienteUseCase = new PatchClienteUseCase(patchClienteAdapter);
        ClienteDTO cliente = patchClienteUseCase.atualizarClientes(clienteDTO);
        if (cliente == null || cliente.getCpf().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

}
