package br.com.fiap.techchallenge.domain.entities.pedido;

import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Predicate;

@Getter
public enum StatusPedidoEnum {
    RECEBIDO(1L, "recebido"),
    EM_PREPARACAO(2L, "preparacao"),
    PRONTO(3L, "pronto"),
    FINALIZADO(4L, "finalizado"),
    CANCELADO(5L, "cancelado");

    private final Long id;
    private final String status;

    StatusPedidoEnum(final Long id, final String status) {
        this.id = id;
        this.status = status;
    }

    public static StatusPedidoEnum byId(Long id) {
        Predicate<StatusPedidoEnum> byId = s -> s.id.equals(id);
        return Arrays.stream(values()).filter(byId).findAny().orElseThrow(() -> new PedidoException(ErrosEnum.PEDIDO_STATUS_NAO_ENCONTRADO));
    }

    public static StatusPedidoEnum byStatus(String status) {
        Predicate<StatusPedidoEnum> byStatus = s -> s.status.equalsIgnoreCase(status);
        return Arrays.stream(values()).filter(byStatus).findAny().orElseThrow(() -> new PedidoException(ErrosEnum.PEDIDO_STATUS_NAO_ENCONTRADO));
    }
}
