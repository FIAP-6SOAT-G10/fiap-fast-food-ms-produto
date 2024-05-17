package br.com.fiap.techchallenge.ports.cliente;

import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;

public interface PostPedidoOutboundPort {
    PedidoDTO realizarCheckout(Long id) throws InterruptedException;
}