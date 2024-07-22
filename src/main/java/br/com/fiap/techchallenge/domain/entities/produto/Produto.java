package br.com.fiap.techchallenge.domain.entities.produto;

import br.com.fiap.techchallenge.domain.entities.pedido.ProdutoPedido;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Produto {
    private Long id;
    private String nome;
    private String descricao;
    private Categoria categoria;
    private BigDecimal preco;
    private String imagem;
    private List<ProdutoPedido> pedidos;

    public Produto() {}

    public Produto(String nome, String descricao, Categoria categoria, BigDecimal preco, String imagem) {
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

        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.imagem = imagem;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public List<ProdutoPedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<ProdutoPedido> pedidos) {
        this.pedidos = pedidos;
    }
}
