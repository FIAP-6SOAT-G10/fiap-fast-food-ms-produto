package br.com.fiap.techchallenge.domain.cors.statuspedido;


import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedidoEnum;
import br.com.fiap.techchallenge.infra.exception.PedidoException;

public class MudancaStatusPedidoRecebido implements IMudancaStatusPedido {

    private final IMudancaStatusPedido next;

    public MudancaStatusPedidoRecebido(IMudancaStatusPedido next) {
        this.next = next;
    }

    @Override
    public void validarMudancaDeStatus(StatusPedidoEnum atual, StatusPedidoEnum novo) {
        if (StatusPedidoEnum.RECEBIDO.equals(atual) && !StatusPedidoEnum.EM_PREPARACAO.equals(novo)) {
            throw new PedidoException(ErrosEnum.PEDIDO_STATUS_RECEBIDO_EM_PREPARACAO);
        } else {
            this.next.validarMudancaDeStatus(atual, novo);
        }
    }
}