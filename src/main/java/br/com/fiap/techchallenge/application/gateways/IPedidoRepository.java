package br.com.fiap.techchallenge.application.gateways;

import br.com.fiap.techchallenge.domain.entities.pagamento.StatusPagamento;
import br.com.fiap.techchallenge.domain.entities.pedido.Pedido;

import java.util.List;

public interface IPedidoRepository {

    Pedido criarPedido(Pedido pedido);

    Pedido atualizarStatusDoPedido(Pedido pedido);

    Pedido atualizarPagamentoDoPedido(Pedido pedido);

    StatusPagamento consultarStatusPagamentoDoPedido(Long id);

    Pedido buscarPedidoPorId(Long id);

    List<Pedido> listarPedidos();

    List<Pedido> listarPedidosPorStatus(String status);

    Pedido realizarCheckout(Long id) throws InterruptedException;

}
