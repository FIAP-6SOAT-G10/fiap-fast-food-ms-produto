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
    private Long id;
    private Cliente cliente;
    private StatusPedido status;
    private BigDecimal valor;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataFinalizacao;
    private LocalDateTime dataCancelamento;
    private StatusPagamento statusPagamento;
    private List<ProdutoPedido> produtoPedidos;
    private Item items;

    public Pedido() {
    }

    public Pedido(Long id, Cliente cliente, StatusPedido status, BigDecimal valor, LocalDateTime dataCriacao, LocalDateTime dataFinalizacao, LocalDateTime dataCancelamento, StatusPagamento statusPagamento, List<ProdutoPedido> produtoPedidos, Item items) {
    this(cliente, status, valor, dataCriacao, dataFinalizacao, dataCancelamento, statusPagamento, produtoPedidos, items);
    this.id = id;
}

    public Pedido(Cliente cliente, Item items) {
        this.cliente = cliente;
        this.items = items;
    }

    public Pedido(Cliente cliente, StatusPedido status, BigDecimal valor, LocalDateTime dataCriacao, LocalDateTime dataFinalizacao, LocalDateTime dataCancelamento, StatusPagamento statusPagamento, List<ProdutoPedido> produtoPedidos, Item items) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente é um campo obrigatório no cadastro de novos pedidos.");
        }

        if (status == null) {
            throw new IllegalArgumentException("Status é um campo obrigatório no cadastro de novos pedidos.");
        }

        if (valor == null || valor.doubleValue() <= 0) {
            throw new IllegalArgumentException("Valor deve ser preenchido com valores acima de R$ 0,00 no cadastro de novos pedidos.");
        }

        if (dataCriacao == null) {
            throw new IllegalArgumentException("Data de criação é um campo obrigatório no cadastro de novos pedidos.");
        }

        if (dataFinalizacao == null) {
            throw new IllegalArgumentException("Data de finalização é um campo obrigatório no cadastro de novos pedidos.");
        }

        if (dataCancelamento == null) {
            throw new IllegalArgumentException("Data de cancelamento é um campo obrigatório no cadastro de novos pedidos.");
        }

        if (statusPagamento == null) {
            throw new IllegalArgumentException("Status de pagamento é um campo obrigatório no cadastro de novos pedidos.");
        }

        if (produtoPedidos == null) {
            throw new IllegalArgumentException("Produtos do pedido é um campo obrigatório no cadastro de novos pedidos.");
        }

        if (items == null) {
            throw new IllegalArgumentException("Items é um campo obrigatório no cadastro de novos pedidos.");
        }

        this.cliente = cliente;
        this.status = status;
        this.valor = valor;
        this.dataCriacao = dataCriacao;
        this.dataFinalizacao = dataFinalizacao;
        this.dataCancelamento = dataCancelamento;
        this.statusPagamento = statusPagamento;
        this.produtoPedidos = produtoPedidos;
        this.items = items;
    }
}
