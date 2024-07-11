package br.com.fiap.techchallenge.domain.statuspagamento;

import br.com.fiap.techchallenge.domain.entities.pagamento.PagamentoPedidoEnum;

public interface MudancaPagamentoPedido {

    void validarMudancaDePagamento(PagamentoPedidoEnum atual, PagamentoPedidoEnum novo);

}
