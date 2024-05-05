package br.com.fiap.techchallenge.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produto")
@SequenceGenerator(name = "produto_seq" , sequenceName = "produto_id_seq" , allocationSize = 1)
public class Produto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_seq")
    private Long id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "descricao", nullable = false, length = 150)
    private String descricao;

    @ManyToOne
    @Enumerated(EnumType.ORDINAL)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @Column(name = "preco", nullable = false, precision = 3, scale = 2)
    private BigDecimal preco;

    @Column(name = "imagem", nullable = false, length = 500)
    private String imagem;

}
