package br.com.fiap.techchallenge.usecases.pedido;

import br.com.fiap.techchallenge.domain.usecases.pedido.PostPedidoUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.ports.cliente.PostPedidoOutboundPort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostPedidoUseCaseTest {

    @InjectMocks
    private PostPedidoUseCase postPedidoUseCase;

    @Mock
    private PostPedidoOutboundPort postPedidoOutboundPort;

    @Test
    public void shouldReturnPedidoDtoWhenCheckoutIsOk() throws InterruptedException {
        PedidoDTO pedidoDTO = new PedidoDTO();
        when(postPedidoOutboundPort.realizarCheckout(1L)).thenReturn(pedidoDTO);

        PedidoDTO result = postPedidoUseCase.realizarCheckout(1L);

        assertEquals(pedidoDTO, result);
    }

    @Test
    public void shouldRetornarNullWhenCheckoutFalhar() throws InterruptedException {
        when(postPedidoOutboundPort.realizarCheckout(1L)).thenReturn(null);

        PedidoDTO result = postPedidoUseCase.realizarCheckout(1L);

        assertEquals(null, result);
    }
}