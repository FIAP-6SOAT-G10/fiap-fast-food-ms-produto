package br.com.fiap.techchallenge.application.gateways;

import br.com.fiap.techchallenge.domain.entities.pedido.Pedido;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface IPedidoRepository {

    Pedido criarPedido(Pedido pedido);

    Pedido atualizarStatusDoPedido(Long aLong, JsonPatch patch);

    Pedido atualizarPagamentoDoPedido(Long aLong, JsonPatch patch);

    Pedido buscarPedidoPorId(Long id);

    List<Pedido> listarPedidos(Integer page, Integer size);

    List<Pedido> listarPedidosPorStatus(String status, Integer page, Integer size);

    Pedido realizarCheckout(Long id) throws InterruptedException;
}
