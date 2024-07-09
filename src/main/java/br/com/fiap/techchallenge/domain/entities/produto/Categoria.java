package br.com.fiap.techchallenge.domain.entities.produto;

import br.com.fiap.techchallenge.infra.dto.CategoriaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Categoria {
    private String nome;
    private String descricao;
    private List<Produto> produtos;

    public Categoria() {}

    public Categoria(CategoriaDTO categoriaDTO) {
        if (categoriaDTO == null) {
            throw new IllegalArgumentException();
        }

        this.nome = CategoriaEnum.fromName(categoriaDTO.getNome()).getNome();
    }

    public Categoria(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}
