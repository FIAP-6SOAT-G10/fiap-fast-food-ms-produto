package br.com.fiap.techchallenge.domain.entities.produto;

import br.com.fiap.techchallenge.domain.entities.pedido.ProdutoPedido;

import java.math.BigDecimal;
import java.util.List;

public class Produto {
    private Long id;
    private String nome;
    private String descricao;
    private Categoria categoria;
    private BigDecimal preco;
    private String imagem;
    private List<ProdutoPedido> pedidos;

    public Produto(Long id, String nome, String descricao, Categoria categoria, BigDecimal preco, String imagem) {
        if (nome == null || nome.isEmpty() || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é um campo obrigatório no cadastro de novos produtos.");
        }

        if (descricao == null || descricao.isEmpty() || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição é um campo obrigatório no cadastro de novos produtos.");
        }

        if (categoria == null) {
            throw new IllegalArgumentException("Categoria do produto é um campo obrigatório no cadastro de novos produtos.");
        }

        if (preco == null || preco.doubleValue() <= 0) {
            throw new IllegalArgumentException("Preço deve ser preenchido com valores acima de R$ 0,00 no cadastro de novos produtos.");
        }

        if (imagem == null) {
            throw new IllegalArgumentException("Imagem do produto é um campo obrigatório no cadastro de novos produtos");
        }

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.imagem = imagem;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getImagem() {
        return imagem;
    }

    public List<ProdutoPedido> getPedidos() {
        return pedidos;
    }
}
