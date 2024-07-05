package br.com.fiap.techchallenge.infra.controllers;


import br.com.fiap.techchallenge.domain.ErrorsResponse;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.naousar.domain.valueobjects.PedidoRequestDTO;
import br.com.fiap.techchallenge.naousar.domain.valueobjects.response.PedidoResponseDTO;
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

import javax.validation.Valid;

@Slf4j
@RestController
@Tag(name = "Pedidos", description = "Conjunto de operações que podem ser realizadas no contexto de pedidos.")
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private static final String VALID_REQUEST = """
            [
                {
                    "op": "replace",
                    "path": "/nome",
                    "value": "NovoNome"
                }
            ]
    """;

    private static final String INVALID_REQUEST = """
            {
                "op": "replace",
                "path": "/nome",
                "value": "NovoNome"
            }
    """;

    @Operation(summary = "Cadastrar Pedido", description = "Esta operação deve ser utilizada para cadastrar um novo pedido no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))})})
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoResponseDTO> cadastrarPedido(@RequestBody @Valid PedidoRequestDTO request) throws BaseException {
        log.info("Criando um pedido.");
        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);
        return ResponseEntity.status(HttpStatus.OK).body(postPedidoUseCase.criarPedido(request));
    }

}
