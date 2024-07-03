package br.com.fiap.techchallenge.infra.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categoria")
@SequenceGenerator(name = "categoria_seq" , sequenceName = "categoria_id_seq" , allocationSize = 1)
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "descricao", nullable = false, length = 150)
    private String descricao;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaEntity")
    private Set<ProdutoEntity> produtoEntities;

    public CategoriaEntity(Long id) {
        this.id = id;
    }

}
