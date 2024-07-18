package br.com.fiap.techchallenge.naousar.ports.pedido;

import br.com.fiap.techchallenge.domain.entities.pedido.Pedido;
import com.github.fge.jsonpatch.JsonPatch;

public interface PatchPedidoOutboundPort {
    Pedido atualizarStatusDoPedido(Long id, JsonPatch patch);
    Pedido atualizarPagamentoDoPedido(Long id, JsonPatch patch);
}
