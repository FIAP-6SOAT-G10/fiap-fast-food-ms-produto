package br.com.fiap.techchallenge.infra.controllers;

import br.com.fiap.techchallenge.application.usecases.pedido.AtualizarPedidoParcialUseCase;
import br.com.fiap.techchallenge.domain.ErrorsResponse;
import br.com.fiap.techchallenge.infra.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.infra.persistence.entities.Pedido;
import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final PedidoMapper pedidoMapper;

    private final AtualizarPedidoParcialUseCase atualizarPedidoParcialUseCase;

    @Operation(summary = "Atualizar Status de Pagamento do Pedido", description = "Esta operação deve ser utilizada para atualizar o status de pagamento de um pedido individualmente", requestBody =
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = {
            @Content(examples = {
                    @ExampleObject(name = "Valid Request", value = PedidoController.VALID_REQUEST),
                    @ExampleObject(name = "Invalid Request", value = PedidoController.INVALID_REQUEST)
            })
    }),
            externalDocs = @ExternalDocumentation(url = "https://jsonpatch.com/", description = "Utilize essa documentação para montar a PATCH request.")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))})})
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PatchMapping(path = "/{id}/pagamento", consumes = "application/json-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDTO> atualizarStatusDePagamento(@PathVariable("id") String id, @RequestBody JsonPatch patch) {
        log.info("Atualizar pagamento pedido.");
        Pedido pedido = atualizarPedidoParcialUseCase.atualizarPagamentoDoPedido(id, patch);

        if (pedido == null) {
            return ResponseEntity.internalServerError().build();
        }
        PedidoDTO pedidoDTO = pedidoMapper.toDTO(pedido);

        return ResponseEntity.ok(pedidoDTO);
    }
}
