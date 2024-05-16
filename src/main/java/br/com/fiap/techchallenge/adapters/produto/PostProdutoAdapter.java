package br.com.fiap.techchallenge.adapters.produto;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.produto.PostProdutoOutboundPort;

public class PostProdutoAdapter implements PostProdutoOutboundPort {
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public PostProdutoAdapter(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Override
    public Produto criarProduto(ProdutoDTO produtoDTO) {
        Produto produto = produtoMapper.fromDTOToEntity(produtoDTO);
        return produtoRepository.saveAndFlush(produto);
    }
}
