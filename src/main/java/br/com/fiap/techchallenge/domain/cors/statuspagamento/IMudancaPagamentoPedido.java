package br.com.fiap.techchallenge.domain.cors.statuspagamento;


import br.com.fiap.techchallenge.domain.entities.pagamento.PagamentoPedidoEnum;

public interface IMudancaPagamentoPedido {

    void validarMudancaDePagamento(PagamentoPedidoEnum atual, PagamentoPedidoEnum novo);

}