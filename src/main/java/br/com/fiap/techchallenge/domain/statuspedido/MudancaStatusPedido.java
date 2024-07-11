package br.com.fiap.techchallenge.domain.statuspedido;//package br.com.fiap.techchallenge.naousar.domain.cors.statuspedido;

import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedidoEnum;

public interface MudancaStatusPedido {

    void validarMudancaDeStatus(StatusPedidoEnum atual, StatusPedidoEnum novo);

}
