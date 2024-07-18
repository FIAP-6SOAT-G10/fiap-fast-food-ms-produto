package br.com.fiap.techchallenge.domain.cors.statuspedido;


import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedidoEnum;
import br.com.fiap.techchallenge.infra.exception.PedidoException;

public class MudancaStatusPedidoFinalizado implements IMudancaStatusPedido {
    @Override
    public void validarMudancaDeStatus(StatusPedidoEnum atual, StatusPedidoEnum novo) {
        if (StatusPedidoEnum.FINALIZADO.equals(atual)) {
            throw new PedidoException(ErrosEnum.PEDIDO_STATUS_FINALIZADO);
        }
    }
}