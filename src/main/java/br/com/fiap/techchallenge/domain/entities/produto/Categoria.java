package br.com.fiap.techchallenge.domain.entities.produto;

import br.com.fiap.techchallenge.infra.controllers.categoria.CategoriaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
