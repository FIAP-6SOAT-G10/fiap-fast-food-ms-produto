package br.com.fiap.techchallenge.ports.pedido;

import br.com.fiap.techchallenge.domain.entities.Pedido;
import com.github.fge.jsonpatch.JsonPatch;

public interface PatchPedidoInboundPort {
    Pedido atualizarStatusDoPedido(String id, JsonPatch patch);
}