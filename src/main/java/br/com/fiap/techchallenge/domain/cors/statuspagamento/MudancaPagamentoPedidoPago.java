package br.com.fiap.techchallenge.domain.cors.statuspagamento;

import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.pagamento.PagamentoPedidoEnum;
import br.com.fiap.techchallenge.infra.exception.PedidoException;

public class MudancaPagamentoPedidoPago implements IMudancaPagamentoPedido {

    private final IMudancaPagamentoPedido next;

    public MudancaPagamentoPedidoPago(IMudancaPagamentoPedido next) {
        this.next = next;
    }

    @Override
    public void validarMudancaDePagamento(PagamentoPedidoEnum atual, PagamentoPedidoEnum novo) {
        if (PagamentoPedidoEnum.PAGO.equals(atual) && !PagamentoPedidoEnum.PAGO.equals(novo)) {
            throw new PedidoException(ErrosEnum.PEDIDO_PAGAMENTO_PAGO);
        } else {
            this.next.validarMudancaDePagamento(atual, novo);
        }
    }
}