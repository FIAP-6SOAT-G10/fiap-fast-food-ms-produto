package br.com.fiap.techchallenge.domain.cors.statuspedido;


import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedidoEnum;
import br.com.fiap.techchallenge.infra.exception.PedidoException;

public class MudancaStatusPedidoPronto implements IMudancaStatusPedido {

    private final IMudancaStatusPedido next;

    public MudancaStatusPedidoPronto(IMudancaStatusPedido next) {
        this.next = next;
    }

    @Override
    public void validarMudancaDeStatus(StatusPedidoEnum atual, StatusPedidoEnum novo) {
        if (StatusPedidoEnum.PRONTO.equals(atual) && !StatusPedidoEnum.FINALIZADO.equals(novo)) {
            throw new PedidoException(ErrosEnum.PEDIDO_STATUS_PRONTO_FINALIZADO);
        } else {
            this.next.validarMudancaDeStatus(atual, novo);
        }
    }
}