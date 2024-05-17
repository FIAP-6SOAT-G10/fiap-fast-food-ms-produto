package br.com.fiap.techchallenge.domain.usecases.pedido;

import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.ports.cliente.PostPedidoInboundPort;
import br.com.fiap.techchallenge.ports.cliente.PostPedidoOutboundPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostPedidoUseCase implements PostPedidoInboundPort {

    private final PostPedidoOutboundPort port;

    public PostPedidoUseCase(PostPedidoOutboundPort postPedidoAdapter) {
        this.port = postPedidoAdapter;
    }

    @Override
    public PedidoDTO realizarCheckout(Long id) throws InterruptedException {
        return port.realizarCheckout(id);
    }
}
