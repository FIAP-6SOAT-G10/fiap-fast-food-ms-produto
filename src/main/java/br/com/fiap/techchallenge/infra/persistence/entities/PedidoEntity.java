package br.com.fiap.techchallenge.infra.persistence.entities;

import br.com.fiap.techchallenge.infra.deserializers.PagamentoPedidoDeserializer;
import br.com.fiap.techchallenge.infra.deserializers.StatusPedidoDeserializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "pedido")
@SequenceGenerator(name = "pedido_seq" , sequenceName = "pedido_id_seq" , allocationSize = 1)
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @JsonBackReference("cliente.pedidos")
    private ClienteEntity cliente;

    @ManyToOne
    @JsonDeserialize(contentUsing = StatusPedidoDeserializer.class)
    @JoinColumn(name = "id_status")
    private StatusPedidoEntity status;

    @Column(name = "valor", scale = 5, precision = 2)
    private BigDecimal valor;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_finalizacao")
    private LocalDateTime dataFinalizacao;

    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;

    @ManyToOne
    @JsonDeserialize(contentUsing = PagamentoPedidoDeserializer.class)
    @JoinColumn(name = "id_status_pagamento")
    private StatusPagamentoEntity statusPagamento;

    @OneToMany(mappedBy = "pedidoEntity", fetch = FetchType.EAGER)
    @JsonBackReference("produtos.pedido")
    private List<ProdutoPedidoEntity> produtos;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public StatusPedidoEntity getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoEntity status) {
        this.status = status;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(LocalDateTime dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public StatusPagamentoEntity getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamentoEntity statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public List<ProdutoPedidoEntity> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoPedidoEntity> produtos) {
        this.produtos = produtos;
    }
}
