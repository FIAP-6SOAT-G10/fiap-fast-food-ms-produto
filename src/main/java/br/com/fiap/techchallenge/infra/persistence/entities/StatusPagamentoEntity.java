package br.com.fiap.techchallenge.infra.persistence.entities;

import br.com.fiap.techchallenge.domain.entities.pagamento.StatusPagamentoEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "status_pagamento")
public class StatusPagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    public StatusPagamentoEntity(String nome) {
        StatusPagamentoEnum statusPagamentoEnum = StatusPagamentoEnum.byStatus(nome);
        this.id = statusPagamentoEnum.getId();
        this.nome = statusPagamentoEnum.getStatus();
    }

}