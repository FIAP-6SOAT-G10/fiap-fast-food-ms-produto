package br.com.fiap.techchallenge.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pedido")
@SequenceGenerator(name = "pedido_seq" , sequenceName = "pedido_id_seq" , allocationSize = 1)
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_status_pedido")
    private StatusPedido status;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_finalizacao")
    private LocalDateTime dataFinalizacao;

    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;

    @ManyToOne
    @JoinColumn(name = "id_status_pagamento")
    private StatusPagamento pagamento;

    @OneToMany(mappedBy = "pedido")
    private List<ProdutoPedido> produtos;
}