package br.com.fiap.techchallenge.domain.cors.statuspagamento;

import br.com.fiap.techchallenge.domain.model.enums.PagamentoPedidoEnum;

public interface MudancaPagamentoPedido {

    void validarMudancaDePagamento(PagamentoPedidoEnum atual, PagamentoPedidoEnum novo);

}