package br.com.fiap.techchallenge.domain.cors.statuspedido;

import br.com.fiap.techchallenge.domain.model.enums.StatusPedidoEnum;

public interface MudancaStatusPedido {

    void validarMudancaDeStatus(StatusPedidoEnum atual, StatusPedidoEnum novo);

}