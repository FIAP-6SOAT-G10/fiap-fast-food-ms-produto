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
import org.mockito.MockitoAnnotations;
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

public class GetPedidoAdapterTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private GetPedidoAdapter getPedidoAdapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void buscarPedidoPorId_ReturnsPedidoDTO_WhenPedidoExists() {
        Pedido pedido = new Pedido();
        PedidoDTO pedidoDTO = new PedidoDTO();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoMapper.toDTO(pedido)).thenReturn(pedidoDTO);

        PedidoDTO result = getPedidoAdapter.buscarPedidoPorId(1L);

        assertEquals(pedidoDTO, result);
    }

    @Test
    public void buscarPedidoPorId_ReturnsNull_WhenPedidoDoesNotExist() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        PedidoDTO result = getPedidoAdapter.buscarPedidoPorId(1L);

        assertNull(result);
    }

    @Test
    public void listarPedidos_ReturnsListOfPedidoDTO_WhenPedidosExist() {
        List<Pedido> pedidos = Arrays.asList(new Pedido(), new Pedido());
        Page<Pedido> page = new PageImpl<>(pedidos);
        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        List<PedidoDTO> pedidosDTO = Arrays.asList(new PedidoDTO(), new PedidoDTO());
        when(pedidoMapper.fromListEntityToListDTO(pedidos)).thenReturn(pedidosDTO);

        List<PedidoDTO> result = getPedidoAdapter.listarPedidos(0, 2);

        assertEquals(pedidosDTO, result);
    }

    @Test
    public void listarPedidos_ReturnsEmptyList_WhenNoPedidosExist() {
        Page<Pedido> page = Page.empty();
        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        List<PedidoDTO> result = getPedidoAdapter.listarPedidos(0, 2);

        assertEquals(0, result.size());
    }
}