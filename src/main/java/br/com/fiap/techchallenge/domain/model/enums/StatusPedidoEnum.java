package br.com.fiap.techchallenge.domain.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.function.Predicate;

@Getter
public enum StatusPedidoEnum {
    RECEBIDO(1L, "recebido"),
    EM_PREPARACAO(2L, "preparacao"),
    PRONTO(3L, "pronto"),
    FINALIZADO(4L, "finalizado");

    private final Long id;
    private final String status;

    StatusPedidoEnum(final Long id, final String status) {
        this.id = id;
        this.status = status;
    }

    public static StatusPedidoEnum byId(Long id) {
        Predicate<StatusPedidoEnum> byId = s -> s.id.equals(id);
        return Arrays.stream(values()).filter(byId).findAny().orElse(null);
    }

    public static StatusPedidoEnum byStatus(String status) {
        Predicate<StatusPedidoEnum> byStatus = s -> s.status.equalsIgnoreCase(status);
        return Arrays.stream(values()).filter(byStatus).findAny().orElse(null);
    }
}
