package br.com.fiap.techchallenge.naousar.ports.cliente;

import br.com.fiap.techchallenge.naousar.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.naousar.domain.valueobjects.PedidoRequestDTO;

public interface PostPedidoOutboundPort {
    PedidoDTO realizarCheckout(Long id) throws InterruptedException;

    PedidoDTO criarPedido(PedidoRequestDTO request);
}