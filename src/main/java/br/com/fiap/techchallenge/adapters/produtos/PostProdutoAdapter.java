package br.com.fiap.techchallenge.adapters.produtos;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.mapper.ProdutoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.produtos.PostProdutoOutboundPort;
import org.springframework.stereotype.Component;

@Component
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
