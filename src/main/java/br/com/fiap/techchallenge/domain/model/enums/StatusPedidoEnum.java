package br.com.fiap.techchallenge.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPedidoEnum {

    RECEBIDO(1L, "Recebido"),
    EM_PREPARAÇÃO(2L, "Em preparação"),
    PRONTO(3L, "Pronto"),
    FINALIZADO(4L, "Finalizado"),
    CANCELADO(5L, "Cancelado");

    private final Long id;
    private final String nome;

}