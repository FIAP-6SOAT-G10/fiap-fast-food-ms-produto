package br.com.fiap.techchallenge.domain.entities.pedido;

import br.com.fiap.techchallenge.domain.entities.cliente.Cliente;
import br.com.fiap.techchallenge.domain.entities.pagamento.StatusPagamento;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Pedido {

    private Cliente cliente;
    private StatusPedido status;
    private BigDecimal valor;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataFinalizacao;
    private LocalDateTime dataCancelamento;
    private StatusPagamento statusPagamento;
    private List<ProdutoPedido> produtoPedidos;
    private Item items;

    public Pedido(Cliente cliente, Item items) {
        this.cliente = cliente;
        this.items = items;
    }
}
