package br.com.fiap.techchallenge.naousar.domain.usecases.pedido;

import br.com.fiap.techchallenge.naousar.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.naousar.ports.pedido.GetPedidoInboundPort;
import br.com.fiap.techchallenge.naousar.ports.pedido.GetPedidoOutboundPort;

import java.util.List;

public class GetPedidoUseCase implements GetPedidoInboundPort {

    private final GetPedidoOutboundPort port;

    public GetPedidoUseCase(GetPedidoOutboundPort getPedidoAdapter) {
        this.port = getPedidoAdapter;
    }

    @Override
    public PedidoDTO buscarPedidoPorId(Long id) {
        return this.port.buscarPedidoPorId(id);
    }

    @Override
    public List<PedidoDTO> listarPedidos(Integer page, Integer size) {
        return this.port.listarPedidos(page, size);
    }

    @Override
    public List<PedidoDTO> listarPedidosPorStatus(String status, Integer page, Integer size) {
        return this.port.listarPedidosPorStatus(status, page, size);
    }
}
