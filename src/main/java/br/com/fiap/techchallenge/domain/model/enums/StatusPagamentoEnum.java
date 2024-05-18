package br.com.fiap.techchallenge.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPagamentoEnum {
    PAGO(1L, "Pago"),
    RECUSADO(2L, "Recusado"),
    PENDENTE(3L, "Pendente");

    private final Long id;
    private final String nome;

}