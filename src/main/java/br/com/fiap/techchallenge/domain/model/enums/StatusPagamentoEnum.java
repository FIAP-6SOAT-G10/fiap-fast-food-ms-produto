package br.com.fiap.techchallenge.domain.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.function.Predicate;

@Getter
public enum StatusPagamentoEnum {
    RECEBIDO(1L, "pago"),
    EM_PREPARACAO(2L, "recusado"),
    PRONTO(3L, "pendente");

    private final Long id;
    private final String status;

    StatusPagamentoEnum(final Long id, final String status) {
        this.id = id;
        this.status = status;
    }

    public static StatusPagamentoEnum byId(Long id) {
        Predicate<StatusPagamentoEnum> byId = s -> s.id.equals(id);
        return Arrays.stream(values()).filter(byId).findAny().orElse(null);
    }

    public static StatusPagamentoEnum byStatus(String status) {
        Predicate<StatusPagamentoEnum> byStatus = s -> s.status.equalsIgnoreCase(status);
        return Arrays.stream(values()).filter(byStatus).findAny().orElse(null);
    }
}
