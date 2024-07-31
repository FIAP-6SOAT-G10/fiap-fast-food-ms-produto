package br.com.fiap.techchallenge.domain.entities.pagamento;

import lombok.Getter;

import java.util.Arrays;
import java.util.function.Predicate;

@Getter
public enum StatusPagamentoEnum {
    PAGO(1L, "pago"),
    RECUSADO(2L, "recusado"),
    PENDENTE(3L, "pendente");

    private Long id;
    private String status;

    StatusPagamentoEnum(Long id, String status) {
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
