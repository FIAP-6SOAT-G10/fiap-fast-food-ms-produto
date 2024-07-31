package br.com.fiap.techchallenge.infra.controllers.cliente;

import br.com.fiap.techchallenge.application.usecases.cliente.AtualizarClienteParcialUseCase;
import br.com.fiap.techchallenge.application.usecases.cliente.AtualizarClienteUseCase;
import br.com.fiap.techchallenge.application.usecases.cliente.CadastrarClienteUseCase;
import br.com.fiap.techchallenge.application.usecases.cliente.ListarClienteUseCase;
import br.com.fiap.techchallenge.domain.ErrorsResponse;
import br.com.fiap.techchallenge.domain.entities.cliente.Cliente;
import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Clientes", description = "Conjunto de operações que podem ser realizadas no contexto de clientes.")
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final CadastrarClienteUseCase cadastrarClienteUseCase;
    private final ListarClienteUseCase listarClienteUseCase;
    private final AtualizarClienteParcialUseCase atualizarClienteParcialUseCase;
    private final AtualizarClienteUseCase atualizarClienteUseCase;

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
    public ResponseEntity<Void> cadastrarCliente(@RequestBody ClienteDTO clienteRequest) {
        log.info("cadastrar um cliente");
        Cliente cliente = cadastrarClienteUseCase.salvarCliente(new Cliente(clienteRequest.getCpf(), clienteRequest.getNome(), clienteRequest.getEmail()));
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<ClienteDTO>> listarTodosClientes(@RequestParam(required = false) String email,
                                                             @RequestParam(required = false) String cpf
    ) {
        List<Cliente> clientes = listarClienteUseCase.listarClientes(email, cpf);
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientes.stream().map(cliente -> new ClienteDTO(cliente.getId(), cliente.getCpf(), cliente.getNome(), cliente.getEmail())).toList());
    }

    @Operation(summary = "Atualizar Dados do Cliente", description = "Está operação consiste em atualizar os dados do cliente cadastrado")
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
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<ClienteDTO> atualizarDadosCliente(@PathVariable("id") String id, @RequestBody JsonPatch patch) {
        log.info("Atualizando cliente.");
        Cliente cliente = atualizarClienteParcialUseCase.atualizarDadosCliente(id, patch);
        return ResponseEntity.ok(new ClienteDTO(cliente.getId(), cliente.getCpf(), cliente.getNome(), cliente.getEmail()));
    }

    @Operation(summary = "Atualizar Cliente", description = "Está operação consiste em atualizar o cliente cadastrado")
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
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))
            })
    })
    @PutMapping(path = "/{id}", consumes = "application/json")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable("id") String id, @RequestBody ClienteDTO clienteRequest
    ) {
        log.info("Atualizando cliente.");
        Cliente cliente = atualizarClienteUseCase.atualizarClientes(id, new Cliente(clienteRequest.getCpf(), clienteRequest.getNome(), clienteRequest.getEmail()));
        if (cliente == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
