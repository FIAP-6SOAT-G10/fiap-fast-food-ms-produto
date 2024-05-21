package br.com.fiap.techchallenge.ports.cliente;

import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoRequestDTO;

public interface PostPedidoOutboundPort {
    PedidoDTO realizarCheckout(Long id) throws InterruptedException;

    PedidoDTO criarPedido(PedidoRequestDTO request);
}