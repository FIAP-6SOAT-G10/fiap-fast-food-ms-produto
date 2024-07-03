package br.com.fiap.techchallenge.naousar.ports.cliente;

import br.com.fiap.techchallenge.naousar.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.naousar.domain.valueobjects.PedidoRequestDTO;
import br.com.fiap.techchallenge.naousar.domain.valueobjects.response.PedidoResponseDTO;

public interface PostPedidoInboundPort {
    PedidoDTO realizarCheckout(Long id) throws InterruptedException;

    PedidoResponseDTO criarPedido(PedidoRequestDTO request);
}
