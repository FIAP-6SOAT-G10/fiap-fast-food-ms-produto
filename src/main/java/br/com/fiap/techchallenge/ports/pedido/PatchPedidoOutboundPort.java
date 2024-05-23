package br.com.fiap.techchallenge.ports.pedido;

import br.com.fiap.techchallenge.domain.entities.Pedido;
import com.github.fge.jsonpatch.JsonPatch;

public interface PatchPedidoOutboundPort {
    Pedido atualizarStatusDoPedido(Long id, JsonPatch patch);
    Pedido atualizarPagamentoDoPedido(Long id, JsonPatch patch);
}