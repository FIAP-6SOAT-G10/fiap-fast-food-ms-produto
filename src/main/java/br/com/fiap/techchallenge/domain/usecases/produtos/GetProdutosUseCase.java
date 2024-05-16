package br.com.fiap.techchallenge.domain.usecases.produtos;

import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.ports.GetProdutosInboundPort;
import br.com.fiap.techchallenge.ports.produtos.GetProdutoOutboundPort;

import java.math.BigDecimal;
import java.util.List;

public class GetProdutosUseCase implements GetProdutosInboundPort {

    private final GetProdutoOutboundPort port;

    public GetProdutosUseCase(GetProdutoOutboundPort port) {
        this.port = port;
    }

    @Override
    public List<ProdutoDTO> listarProdutos(Integer page, Integer size, String nome, String descricao, BigDecimal preco) {
        return port.listarProdutos(page, size, nome, descricao, preco);
    }
}
