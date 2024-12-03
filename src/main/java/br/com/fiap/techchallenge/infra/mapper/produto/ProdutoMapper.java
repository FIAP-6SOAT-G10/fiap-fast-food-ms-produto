package br.com.fiap.techchallenge.infra.mapper.produto;

import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.persistence.document.ProdutoDocument;
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
        Categoria categoria = new Categoria(categoriaEntity.getNome(), categoriaEntity.getDescricao());
        return new Produto(produtoEntity.getId(), produtoEntity.getNome(), produtoEntity.getDescricao(), categoria, produtoEntity.getPreco(), produtoEntity.getImagem());
    }

    public List<Produto> fromListEntityToListDomain(List<ProdutoEntity> produtos) {
        return produtos.stream().map(this::fromEntityToDomain).toList();
    }


    public ProdutoDocument fromEntityToCache(ProdutoEntity produtoEntity) {
        CategoriaEntity categoriaEntity = produtoEntity.getCategoriaEntity();
        Categoria categoria = new Categoria(categoriaEntity.getNome(), categoriaEntity.getDescricao());
        ProdutoDocument produtoDocument = new ProdutoDocument();
        produtoDocument.setId(produtoEntity.getId());
        produtoDocument.setNome(produtoEntity.getNome());
        produtoDocument.setDescricao(produtoEntity.getDescricao());
        produtoDocument.setCategoria(categoria);
        produtoDocument.setPreco(produtoEntity.getPreco());
        produtoDocument.setImagem(produtoEntity.getImagem());

        return produtoDocument;
    }

    public List<ProdutoDocument> fromEntityListToCacheList(List<ProdutoEntity> produtos) {
        return produtos.stream().map(this::fromEntityToCache).toList();
    }

    public ProdutoEntity fromCacheToEntity(ProdutoDocument produtoDocument) {

        Categoria categoria = produtoDocument.getCategoria();

        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNome(categoria.getNome());
        categoriaEntity.setDescricao(categoria.getDescricao());

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(produtoDocument.getId());
        produtoEntity.setNome(produtoDocument.getNome());
        produtoEntity.setDescricao(produtoDocument.getDescricao());
        produtoEntity.setCategoriaEntity(categoriaEntity);
        produtoEntity.setPreco(produtoDocument.getPreco());
        produtoEntity.setImagem(produtoDocument.getImagem());

        return produtoEntity;
    }

    public List<ProdutoEntity> fromCacheListToEntityList(List<ProdutoDocument> produtos) {
        return produtos.stream().map(this::fromCacheToEntity).toList();
    }
}