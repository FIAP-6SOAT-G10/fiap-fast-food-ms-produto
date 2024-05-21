package br.com.fiap.techchallenge.ports.cliente;

import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoRequestDTO;
import br.com.fiap.techchallenge.domain.valueobjects.response.PedidoResponseDTO;

public interface PostPedidoInboundPort {
    PedidoDTO realizarCheckout(Long id) throws InterruptedException;

    PedidoResponseDTO criarPedido(PedidoRequestDTO request);
}
