package br.com.fiap.techchallenge.domain.entities.pagamento;

import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Predicate;

@Getter
public enum PagamentoPedidoEnum {

    PAGO(1L, "pago"),
    RECUSADO(2L, "recusado"),
    PENDENTE(3L, "pendente");

    private final Long id;
    private final String status;

    PagamentoPedidoEnum(final Long id, final String status) {
        this.id = id;
        this.status = status;
    }

    public static PagamentoPedidoEnum byId(Long id) {
        Predicate<PagamentoPedidoEnum> byId = s -> s.id.equals(id);
        return Arrays.stream(values()).filter(byId).findAny().orElseThrow(() -> new PedidoException(ErrosEnum.PEDIDO_PAGAMENTO_PAGAMENTO_NAO_ENCONTRADO));
    }

    public static PagamentoPedidoEnum byStatus(String status) {
        Predicate<PagamentoPedidoEnum> byStatus = s -> s.status.equalsIgnoreCase(status);
        return Arrays.stream(values()).filter(byStatus).findAny().orElseThrow(() -> new PedidoException(ErrosEnum.PEDIDO_PAGAMENTO_PAGAMENTO_NAO_ENCONTRADO));
    }
}
