package br.com.fiap.techchallenge.adapters;

import br.com.fiap.techchallenge.adapters.pedido.PostPedidoAdapter;
import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PostPedidoAdapterTest {

    @InjectMocks
    private PostPedidoAdapter postPedidoAdapter;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnPedidoDtoWhenCheckoutIsSuccessful() throws InterruptedException {
        Pedido pedido = new Pedido();
        PedidoDTO pedidoDTO = new PedidoDTO();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoMapper.toDTO(any(Pedido.class))).thenReturn(pedidoDTO);
        when(pedidoMapper.toEntity(any(PedidoDTO.class))).thenReturn(pedido);
        when(pedidoRepository.saveAndFlush(any(Pedido.class))).thenReturn(pedido);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoMapper.toDTO(pedido)).thenReturn(pedidoDTO);

        PedidoDTO result = postPedidoAdapter.realizarCheckout(1L);

        assertEquals(pedidoDTO, result);
    }

    @Test
    public void shouldThrowPedidoExceptionWhenPedidoDoesNotExist() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PedidoException.class, () -> postPedidoAdapter.realizarCheckout(1L));
    }
}