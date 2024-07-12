package br.com.fiap.techchallenge.naousar.ports.pedido;

import br.com.fiap.techchallenge.infra.persistence.entities.PedidoEntity;
import com.github.fge.jsonpatch.JsonPatch;

public interface PatchPedidoOutboundPort {
    PedidoEntity atualizarStatusDoPedido(Long id, JsonPatch patch);
    PedidoEntity atualizarPagamentoDoPedido(Long id, JsonPatch patch);
}