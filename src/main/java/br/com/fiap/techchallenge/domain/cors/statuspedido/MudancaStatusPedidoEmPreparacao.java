package br.com.fiap.techchallenge.domain.cors.statuspedido;

import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.model.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.infra.exception.PedidoException;

public class MudancaStatusPedidoEmPreparacao implements MudancaStatusPedido {

    private final MudancaStatusPedido next;

    public MudancaStatusPedidoEmPreparacao(MudancaStatusPedido next) {
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