package br.com.fiap.techchallenge.domain.entities.produto;

import br.com.fiap.techchallenge.infra.controllers.categoria.dtos.CategoriaDTO;

import java.util.List;


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

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}
