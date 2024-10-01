package br.com.fiap.techchallenge.domain.entities.pedido;

import br.com.fiap.techchallenge.domain.entities.produto.Produto;

import java.math.BigDecimal;
import java.math.BigInteger;


public class ProdutoPedido {
    private Long id;
    private Produto produto;
    private Pedido pedido;
    private BigDecimal valorTotal;
    private BigInteger quantidade;

    public ProdutoPedido() {}

    public ProdutoPedido(Produto produto, BigDecimal valorTotal, BigInteger quantidade) {
        this.produto = produto;
        this.valorTotal = valorTotal;
        this.quantidade = quantidade;
    }

    public ProdutoPedido(Long id, Produto produto, BigDecimal valorTotal, BigInteger quantidade) {
        this.id = id;
        this.produto = produto;
        this.valorTotal = valorTotal;
        this.quantidade = quantidade;
    }

    public ProdutoPedido(Long id, Produto produto, Pedido pedido, BigDecimal valorTotal, BigInteger quantidade) {
        this.id = id;
        this.produto = produto;
        this.pedido = pedido;
        this.valorTotal = valorTotal;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public BigInteger getQuantidade() {
        return quantidade;
    }
}
