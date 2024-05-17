package br.com.fiap.techchallenge.usecases.pedido;

import br.com.fiap.techchallenge.adapters.pedido.GetPedidoAdapter;
import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.entities.StatusPagamento;
import br.com.fiap.techchallenge.domain.entities.StatusPedido;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.usecases.pedido.GetPedidoUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import br.com.fiap.techchallenge.ports.pedido.GetPedidoOutboundPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GetPedidoUseCaseTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    private GetPedidoOutboundPort port;

    private GetPedidoUseCase getPedidoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        port = new GetPedidoAdapter(pedidoRepository, pedidoMapper);
        getPedidoUseCase = new GetPedidoUseCase(port);
    }

    @Test
    void itShouldRetornarNenhumPedido(){
        Long id = 15L;
        when(pedidoRepository.findById(id)).thenReturn(null);

        PedidoDTO pedido = getPedidoUseCase.buscarPedidoPorId(id);
        assertNull(pedido);
    }

    @Test
    void itShouldRetornarPedido(){
        Long id = 15L;
        Pedido pedidoMock = new Pedido(
                id,
                new Cliente(1L, "123", "Nome", "email@email.com"),
                new StatusPedido(1L, "Em Espera"),
                new BigDecimal("10.5"),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                new StatusPagamento(1L, "Aprovado"),
                new ArrayList<>()
        );
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoMock));

        PedidoDTO pedidoDTO = getPedidoUseCase.buscarPedidoPorId(id);
        assertEquals(pedidoMock.getId(), pedidoDTO.getId());
        assertEquals(pedidoMock.getCliente().getId(), pedidoDTO.getCliente().getId());
    }

    @Test
    void shouldReturnEmptyListWhenNoPedidosExist() {
        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(Page.empty());

        List<PedidoDTO> result = getPedidoUseCase.listarPedidos(0, 2);

        assertTrue(result.isEmpty());
    }

}
