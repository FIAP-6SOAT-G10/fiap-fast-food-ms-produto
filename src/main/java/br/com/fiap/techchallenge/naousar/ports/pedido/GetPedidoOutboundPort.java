package br.com.fiap.techchallenge.naousar.ports.pedido;

import br.com.fiap.techchallenge.naousar.domain.valueobjects.PedidoDTO;

import java.util.List;

public interface GetPedidoOutboundPort {

    PedidoDTO buscarPedidoPorId(Long id);

    List<PedidoDTO> listarPedidos(Integer page, Integer size);

    List<PedidoDTO> listarPedidosPorStatus(String status, Integer page, Integer size);

}