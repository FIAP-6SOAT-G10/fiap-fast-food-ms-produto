package br.com.fiap.techchallenge.domain.statuspedido;

import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedidoEnum;

public interface MudancaStatusPedido {

    void validarMudancaDeStatus(StatusPedidoEnum atual, StatusPedidoEnum novo);

}
