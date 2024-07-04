package br.com.fiap.techchallenge.infra.mapper.produto;

import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;

import java.util.List;

public class ProdutoMapper {

    public ProdutoEntity fromDomainToEntity(Produto produto) {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNome(produto.getCategoria().getNome());
        categoriaEntity.setDescricao(produto.getCategoria().getDescricao());

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(produto.getId());
        produtoEntity.setNome(produto.getNome());
        produtoEntity.setDescricao(produto.getDescricao());
        produtoEntity.setCategoriaEntity(categoriaEntity);
        produtoEntity.setPreco(produto.getPreco());
        produtoEntity.setImagem(produto.getImagem());

        return produtoEntity;
    }

    public Produto fromEntityToDomain(ProdutoEntity produtoEntity) {
        CategoriaEntity categoriaEntity = produtoEntity.getCategoriaEntity();

        Categoria categoria = new Categoria();
        categoria.setNome(categoriaEntity.getNome());
        categoria.setDescricao(categoriaEntity.getDescricao());

        Produto produto = new Produto();
        produto.setId(produtoEntity.getId());
        produto.setNome(produtoEntity.getNome());
        produto.setDescricao(produtoEntity.getDescricao());
        produto.setCategoria(categoria);
        produto.setPreco(produtoEntity.getPreco());
        produto.setImagem(produtoEntity.getImagem());

        return produto;
    }

    public List<Produto> fromListEntityToListDomain(List<ProdutoEntity> produtos) {
        return produtos.stream().map(this::fromEntityToDomain).toList();
    }

}