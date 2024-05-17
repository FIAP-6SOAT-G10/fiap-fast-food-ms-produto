package br.com.fiap.techchallenge.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "pedido")
@SequenceGenerator(name = "pedido_seq" , sequenceName = "pedido_id_seq" , allocationSize = 1)
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @JsonBackReference
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_status")
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