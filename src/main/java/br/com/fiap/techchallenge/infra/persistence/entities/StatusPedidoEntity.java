package br.com.fiap.techchallenge.infra.persistence.entities;

import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedidoEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "status_pedido")
public class StatusPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    public StatusPedidoEntity() {}

    public StatusPedidoEntity(String nome) {
        StatusPedidoEnum statusPedidoEnum = StatusPedidoEnum.byStatus(nome);
        this.id = statusPedidoEnum.getId();
        this.nome = statusPedidoEnum.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}