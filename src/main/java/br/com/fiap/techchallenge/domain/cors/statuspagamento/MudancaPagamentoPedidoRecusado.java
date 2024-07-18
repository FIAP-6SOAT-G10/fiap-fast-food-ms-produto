package br.com.fiap.techchallenge.domain.cors.statuspagamento;

import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.pagamento.PagamentoPedidoEnum;
import br.com.fiap.techchallenge.infra.exception.PedidoException;

public class MudancaPagamentoPedidoRecusado implements IMudancaPagamentoPedido {

    @Override
    public void validarMudancaDePagamento(PagamentoPedidoEnum atual, PagamentoPedidoEnum novo) {
        if (PagamentoPedidoEnum.RECUSADO.equals(atual) && !PagamentoPedidoEnum.RECUSADO.equals(novo)) {
            throw new PedidoException(ErrosEnum.PEDIDO_PAGAMENTO_RECUSADO);
        }
    }
}