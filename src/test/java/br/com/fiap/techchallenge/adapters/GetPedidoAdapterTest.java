package br.com.fiap.techchallenge.adapters;

import br.com.fiap.techchallenge.adapters.pedido.GetPedidoAdapter;
import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GetPedidoAdapterTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private GetPedidoAdapter getPedidoAdapter;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void shouldBuscarPedidoPorIdReturnsPedidoDTOWhenPedidoExists() {
        Pedido pedido = new Pedido();
        PedidoDTO pedidoDTO = new PedidoDTO();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoMapper.toDTO(pedido)).thenReturn(pedidoDTO);

        PedidoDTO result = getPedidoAdapter.buscarPedidoPorId(1L);

        assertEquals(pedidoDTO, result);
    }

    @Test
    public void mustbuscarPedidoPorIdReturnsNullWhenPedidoDoesNotExist() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        PedidoDTO result = getPedidoAdapter.buscarPedidoPorId(1L);

        assertNull(result);
    }

    @Test
    public void shouldListarPedidosReturnsListOfPedidoDTOWhenPedidosExist() {
        List<Pedido> pedidos = Arrays.asList(new Pedido(), new Pedido());
        Page<Pedido> page = new PageImpl<>(pedidos);
        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        List<PedidoDTO> pedidosDTO = Arrays.asList(new PedidoDTO(), new PedidoDTO());
        when(pedidoMapper.fromListEntityToListDTO(pedidos)).thenReturn(pedidosDTO);

        List<PedidoDTO> result = getPedidoAdapter.listarPedidos(0, 2);

        assertEquals(pedidosDTO, result);
    }

    @Test
    public void mustListarPedidosReturnsEmptyListWhenNoPedidosExist() {
        Page<Pedido> page = Page.empty();
        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        List<PedidoDTO> result = getPedidoAdapter.listarPedidos(0, 2);

        assertEquals(0, result.size());
    }
}