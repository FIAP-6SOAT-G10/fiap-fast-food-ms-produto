package br.com.fiap.techchallenge.infra.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produto_pedido")
@SequenceGenerator(name = "produto_pedido_seq" , sequenceName = "produto_pedido_id_seq" , allocationSize = 1)
public class ProdutoPedidoEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_pedido_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private ProdutoEntity produtoEntity;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_pedido")
    private PedidoEntity pedidoEntity;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "quantidade")
    private BigInteger quantidade;

}