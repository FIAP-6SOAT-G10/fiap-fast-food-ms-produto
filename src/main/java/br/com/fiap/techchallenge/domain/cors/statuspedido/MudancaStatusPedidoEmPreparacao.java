package br.com.fiap.techchallenge.domain.cors.statuspedido;


import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedidoEnum;
import br.com.fiap.techchallenge.infra.exception.PedidoException;

public class MudancaStatusPedidoEmPreparacao implements IMudancaStatusPedido {

    private final IMudancaStatusPedido next;

    public MudancaStatusPedidoEmPreparacao(IMudancaStatusPedido next) {
        this.next = next;
    }

    @Override
    public void validarMudancaDeStatus(StatusPedidoEnum atual, StatusPedidoEnum novo) {
        if (StatusPedidoEnum.EM_PREPARACAO.equals(atual) && !StatusPedidoEnum.PRONTO.equals(novo)) {
            throw new PedidoException(ErrosEnum.PEDIDO_STATUS_EM_PREPARACAO_PRONTO);
        } else {
            this.next.validarMudancaDeStatus(atual, novo);
        }
    }
}