package br.com.fiap.techchallenge.domain.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.function.Predicate;

@Getter
public enum StatusPedidoEnum {
    RECEBIDO(1, "recebido"),
    EM_PREPARACAO(2, "preparacao"),
    PRONTO(3, "pronto"),
    FINALIZADO(4, "finalizado");

    private final int id;
    private final String status;

    StatusPedidoEnum(final int id, final String status) {
        this.id = id;
        this.status = status;
    }

    public static StatusPedidoEnum byId(Long id) {
        Predicate<StatusPedidoEnum> byId = s -> s.id == id.intValue();
        return Arrays.stream(values()).filter(byId).findFirst().orElse(null);
    }

    public static StatusPedidoEnum byStatus(String status) {
        Predicate<StatusPedidoEnum> byStatus = s -> s.status.equalsIgnoreCase(status);
        return Arrays.stream(values()).filter(byStatus).findFirst().orElse(null);
    }
}
