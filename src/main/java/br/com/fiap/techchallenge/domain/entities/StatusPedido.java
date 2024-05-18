package br.com.fiap.techchallenge.domain.entities;

import br.com.fiap.techchallenge.domain.model.enums.StatusPedidoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "status_pedido")
public class StatusPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    public StatusPedido(String nome) {
        StatusPedidoEnum statusPedidoEnum = StatusPedidoEnum.byStatus(nome);
        this.id = statusPedidoEnum.getId();
        this.nome = statusPedidoEnum.getStatus();
    }

}