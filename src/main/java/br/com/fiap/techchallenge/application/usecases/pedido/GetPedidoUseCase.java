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

    public List<Pedido> listarPedidos() {
        return pedidoRepository.listarPedidos();
    }

    public List<Pedido> listarPedidosPorStatus(String status) {
        return pedidoRepository.listarPedidosPorStatus(status);
    }

    public StatusPagamento consultarStatusPagamentoDoPedido(Long id) {
        return pedidoRepository.consultarStatusPagamentoDoPedido(id);
    }

}
