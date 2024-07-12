package br.com.fiap.techchallenge.infra.persistence.entities;

import br.com.fiap.techchallenge.naousar.domain.deserializers.PagamentoPedidoDeserializer;
import br.com.fiap.techchallenge.naousar.domain.deserializers.StatusPedidoDeserializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private ClienteEntity clienteEntity;

    @ManyToOne
    @JsonDeserialize(contentUsing = StatusPedidoDeserializer.class)
    @JoinColumn(name = "id_status")
    private StatusPedido status;

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
    private StatusPagamento statusPagamento;

    @OneToMany(mappedBy = "pedido")
    @JsonBackReference("produtos.pedido")
    private List<ProdutoPedidoEntity> produtos;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

}