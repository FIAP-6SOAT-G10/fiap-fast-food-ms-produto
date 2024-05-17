package br.com.fiap.techchallenge.ports.pedido;

import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;

public interface GetPedidoInboundPort {

    PedidoDTO buscarPedidoPorId(Long id);

}
