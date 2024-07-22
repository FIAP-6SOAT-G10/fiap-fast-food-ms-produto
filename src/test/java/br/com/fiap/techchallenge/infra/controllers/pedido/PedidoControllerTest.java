package br.com.fiap.techchallenge.infra.controllers.pedido;

import br.com.fiap.techchallenge.application.usecases.pedido.GetPedidoUseCase;
import br.com.fiap.techchallenge.application.usecases.pedido.PatchPedidoUseCase;
import br.com.fiap.techchallenge.application.usecases.pedido.PostPedidoUseCase;
import br.com.fiap.techchallenge.domain.entities.pagamento.StatusPagamento;
import br.com.fiap.techchallenge.domain.entities.pedido.Pedido;
import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedido;
import br.com.fiap.techchallenge.infra.controllers.cliente.ClienteDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PostPedidoUseCase postPedidoUseCase;

    @Mock
    private GetPedidoUseCase getPedidoUseCase;

    @Mock
    private PatchPedidoUseCase patchPedidoUseCase;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarPedidoWithoutItems() throws BaseException {
        PedidoDTO pedidoDTO = new PedidoDTO(1L, new ClienteDTO(1L, "42321973898", "silva da silva", "any()email"), new StatusPedido(1L, "any()status"), BigDecimal.TEN, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), null, null, null);
        pedidoDTO.setCliente(new ClienteDTO(1L, "42321973898", "silva da silva", "any()email"));
        ResponseEntity<Pedido> response = pedidoController.cadastrarPedido(pedidoDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldReturnPedidoListWhenListarPedidosPorStatusIsCalled() {
        List<Pedido> expected = Collections.singletonList(new Pedido());
        when(getPedidoUseCase.listarPedidosPorStatus(anyString(), anyInt(), anyInt())).thenReturn(expected);

        ResponseEntity<List<Pedido>> response = pedidoController.listarPedidosPorStatus("status", 0, 25);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void shouldReturnPedidoWhenListarPedidoPorIdIsCalled() {
        Pedido expected = new Pedido();
        when(getPedidoUseCase.buscarPedidoPorId(anyLong())).thenReturn(expected);

        ResponseEntity<Pedido> response = pedidoController.listarPedidoPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void shouldReturnNotFoundWhenListarPedidoPorIdIsCalledAndPedidoDoesNotExist() {
        when(getPedidoUseCase.buscarPedidoPorId(anyLong())).thenReturn(null);

        ResponseEntity<Pedido> response = pedidoController.listarPedidoPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldUpdatePaymentStatusSuccessfully() throws Exception {
        JsonPatch validPatch = JsonPatch.fromJson(objectMapper.readTree("[{\"op\":\"replace\",\"path\":\"/statusPagamento\",\"value\":\"PAGO\"}]"));
        Pedido expectedPedido = new Pedido();
        expectedPedido.setStatusPagamento(StatusPagamento.builder().nome("PAGO").build());
        when(patchPedidoUseCase.atualizarPagamentoDoPedido(eq("1"), any(JsonPatch.class))).thenReturn(expectedPedido);

        ResponseEntity<Pedido> response = pedidoController.atualizarStatusDePagamento("1", validPatch);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(StatusPagamento.builder().nome("PAGO").build(), response.getBody().getStatusPagamento());
    }

    @Test
    public void shouldReturnInternalServerErrorWhenPatchIsInvalid() throws Exception {
        JsonPatch invalidPatch = JsonPatch.fromJson(objectMapper.readTree("[{\"op\":\"replace\",\"path\":\"/statusPagamento\",\"value\":\"INVALIDO\"}]"));
        when(patchPedidoUseCase.atualizarPagamentoDoPedido(eq("1"), any(JsonPatch.class))).thenReturn(null);

        ResponseEntity<Pedido> response = pedidoController.atualizarStatusDePagamento("1", invalidPatch);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
