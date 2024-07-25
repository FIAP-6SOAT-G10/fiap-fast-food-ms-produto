package br.com.fiap.techchallenge.infra.mapper.produtopedido;


import br.com.fiap.techchallenge.domain.entities.pedido.ProdutoPedido;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.mapper.categoria.CategoriaMapper;
import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoPedidoEntity;

import java.util.List;

public class ProdutoPedidoMapper {

    public ProdutoPedido fromEntityToDomain(ProdutoPedidoEntity produtoPedidoEntity) {
        ProdutoEntity produtoEntity = produtoPedidoEntity.getProdutoEntity();

        CategoriaEntity categoriaEntity = produtoEntity.getCategoriaEntity();
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaEntity.getNome());
        categoria.setDescricao(categoriaEntity.getDescricao());

        Produto produto = new Produto();
        produto.setId(produtoEntity.getId());
        produto.setNome(produtoEntity.getNome());
        produto.setPreco(produtoEntity.getPreco());
        produto.setImagem(produtoEntity.getImagem());
        produto.setDescricao(produtoEntity.getDescricao());
        produto.setCategoria(categoria);

        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setProduto(produto);
        produtoPedido.setQuantidade(produtoPedidoEntity.getQuantidade());
        produtoPedido.setValorTotal(produtoPedidoEntity.getValorTotal());

        return produtoPedido;
    }

    public ProdutoPedidoEntity fromDomainToEntity(ProdutoPedido produtoPedido) {
        CategoriaMapper categoriaMapper = new CategoriaMapper();
        CategoriaEntity categoriaEntity = categoriaMapper.fromDomainToEntity(CategoriaEnum.fromName(produtoPedido.getProduto().getCategoria().getNome()));

        Produto produto = produtoPedido.getProduto();
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(produto.getId());
        produtoEntity.setCategoriaEntity(categoriaEntity);
        produtoEntity.setNome(produto.getNome());
        produtoEntity.setDescricao(produto.getDescricao());
        produtoEntity.setPreco(produto.getPreco());
        produtoEntity.setImagem(produto.getImagem());

        ProdutoPedidoEntity produtoPedidoEntity = new ProdutoPedidoEntity();
        produtoPedidoEntity.setProdutoEntity(produtoEntity);
        produtoPedidoEntity.setQuantidade(produtoPedido.getQuantidade());
        produtoPedidoEntity.setValorTotal(produtoPedido.getValorTotal());

        return produtoPedidoEntity;
    }

    public List<ProdutoPedido> fromListEntityToListDomain(List<ProdutoPedidoEntity> produtosPedido) {
        return produtosPedido.stream().map(this::fromEntityToDomain).toList();
    }

    public List<ProdutoPedidoEntity> fromListDomainToListEntity(List<ProdutoPedido> produtosPedido) {
        return produtosPedido.stream().map(this::fromDomainToEntity).toList();
    }

}
