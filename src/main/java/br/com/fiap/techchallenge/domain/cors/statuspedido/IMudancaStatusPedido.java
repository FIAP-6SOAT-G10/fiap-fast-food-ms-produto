package br.com.fiap.techchallenge.domain.cors.statuspedido;

import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedidoEnum;

public interface IMudancaStatusPedido {

    void validarMudancaDeStatus(StatusPedidoEnum atual, StatusPedidoEnum novo);
}
