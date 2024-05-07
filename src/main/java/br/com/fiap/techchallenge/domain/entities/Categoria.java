package br.com.fiap.techchallenge.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categoria")
@SequenceGenerator(name = "categoria_seq" , sequenceName = "categoria_id_seq" , allocationSize = 1)
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "descricao", nullable = false, length = 150)
    private String descricao;

    @OneToMany(mappedBy = "categoria")
    private Set<Produto> produtos;
}
