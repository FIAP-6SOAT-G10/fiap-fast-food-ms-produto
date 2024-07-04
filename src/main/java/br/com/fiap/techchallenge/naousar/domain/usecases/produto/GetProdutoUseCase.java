package br.com.fiap.techchallenge.naousar.domain.usecases.produto;

import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import br.com.fiap.techchallenge.naousar.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.naousar.ports.produto.GetProdutoInboundPort;
import br.com.fiap.techchallenge.naousar.ports.produto.GetProdutoOutboundPort;

import java.math.BigDecimal;
import java.util.List;

public class GetProdutoUseCase implements GetProdutoInboundPort {

    private final GetProdutoOutboundPort port;

    public GetProdutoUseCase(GetProdutoOutboundPort port) {
        this.port = port;
    }

    @Override
    public List<ProdutoDTO> listarProdutos(Integer page, Integer size, String nome, String descricao, BigDecimal preco) {
        return this.port.listarProdutos(page, size, nome, descricao, preco);
    }

    @Override
    public List<ProdutoEntity> listarProdutosPorCategoria(String categoria) {
        CategoriaEnum categoriaEnum = CategoriaEnum.fromName(categoria);
        return this.port.listarProdutosPorCategoria(categoriaEnum);
    }
}
