package br.com.fiap.techchallenge.naousar.ports.pedido;

import br.com.fiap.techchallenge.infra.persistence.entities.PedidoEntity;
import com.github.fge.jsonpatch.JsonPatch;

public interface PatchPedidoInboundPort {
    PedidoEntity atualizarStatusDoPedido(String id, JsonPatch patch);
    PedidoEntity atualizarPagamentoDoPedido(String id, JsonPatch patch);
}