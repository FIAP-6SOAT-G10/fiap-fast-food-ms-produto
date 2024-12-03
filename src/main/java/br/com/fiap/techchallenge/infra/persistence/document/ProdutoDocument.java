package br.com.fiap.techchallenge.infra.persistence.document;

import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoDocument {

    @Id
    private Long id;
    private String nome;
    private String descricao;
    private Categoria categoria;
    private BigDecimal preco;
    private String imagem;

    public ProdutoDocument() {}

    public ProdutoDocument(String nome, String descricao, Categoria categoria, BigDecimal preco, String imagem) {
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.imagem = imagem;
    }
}
