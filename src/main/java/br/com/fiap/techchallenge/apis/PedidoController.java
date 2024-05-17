package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.pedido.GetPedidoAdapter;
import br.com.fiap.techchallenge.adapters.pedido.PostPedidoAdapter;
import br.com.fiap.techchallenge.domain.model.ErrorsResponse;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.usecases.pedido.GetPedidoUseCase;
import br.com.fiap.techchallenge.domain.usecases.pedido.PostPedidoUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import br.com.fiap.techchallenge.ports.cliente.PostPedidoOutboundPort;
import br.com.fiap.techchallenge.ports.pedido.GetPedidoInboundPort;
import br.com.fiap.techchallenge.ports.pedido.GetPedidoOutboundPort;
import br.com.fiap.techchallenge.ports.cliente.PostPedidoInboundPort;
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
@Tag(name = "Pedidos", description = "Conjunto de operações que podem ser realizadas no contexto de pedidos.")
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    private final PedidoMapper pedidoMapper;

    @Operation(summary = "Lista o produto em especifico", description = "Está operação consiste em retornar as informações do produto em específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "204", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            })
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<PedidoDTO> listarPedidoPorId(@PathVariable("id") Long id) {
        log.info("Buscando pedidos por id.");
        GetPedidoOutboundPort getPedidoAdapter = new GetPedidoAdapter(pedidoRepository, pedidoMapper);
        GetPedidoInboundPort getPedidoUseCase = new GetPedidoUseCase(getPedidoAdapter);
        PedidoDTO pedido = getPedidoUseCase.buscarPedidoPorId(id);
        if (pedido == null) {
            log.error("Pedido não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(pedido);
    }

    @Operation(summary = "Lista todos os pedidops", description = "Está operação consiste em retornar as informações de todos os pedidos")
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
    public ResponseEntity<List<PedidoDTO>> listarTodosPedidos(@RequestParam Integer page,
                                                              @RequestParam Integer size) {
        log.info("Buscando pedidos.");
        GetPedidoOutboundPort getPedidoAdapter = new GetPedidoAdapter(pedidoRepository, pedidoMapper);
        GetPedidoInboundPort getPedidoUseCase = new GetPedidoUseCase(getPedidoAdapter);
        List<PedidoDTO> listaPedidos = getPedidoUseCase.listarPedidos(page, size);
        if (listaPedidos == null || listaPedidos.isEmpty()) {
            log.error("Pedidos não encontrados.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaPedidos);
    }

    @Operation(summary = "Realizar checkout", description = "Esta operação consiste em realizar o checkout de um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))})})
    @PostMapping(path = "/{id}/checkout")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Void> realizarCheckout(@PathVariable("id") Long id) throws InterruptedException {
        log.info("Realizando checkout.");
        PostPedidoOutboundPort getPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(getPedidoAdapter);

        PedidoDTO pedidoDTO = postPedidoUseCase.realizarCheckout(id);
        if (pedidoDTO == null) {
            log.error("Pedido não encontrado pra realizar checkout.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        log.info("Checkout realizado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}