package br.com.fiap.techchallenge.application.usecases.pedido;


import br.com.fiap.techchallenge.application.gateways.IPedidoRepository;
import br.com.fiap.techchallenge.domain.entities.pagamento.StatusPagamento;
import br.com.fiap.techchallenge.domain.entities.pedido.Pedido;

import java.util.List;

public class GetPedidoUseCase {

    private final IPedidoRepository pedidoRepository;

    public GetPedidoUseCase(IPedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.buscarPedidoPorId(id);
    }

    public List<Pedido> listarPedidos(Integer page, Integer size) {
        return pedidoRepository.listarPedidos(page, size);
    }

    public List<Pedido> listarPedidosPorStatus(String status, Integer page, Integer size) {
        return pedidoRepository.listarPedidosPorStatus(status, page, size);
    }

    public StatusPagamento consultarStatusPagamentoDoPedido(Long id) {
        return pedidoRepository.consultarStatusPagamentoDoPedido(id);
    }

}
