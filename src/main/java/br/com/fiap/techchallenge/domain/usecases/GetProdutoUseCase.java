package br.com.fiap.techchallenge.domain.usecases;

import br.com.fiap.techchallenge.domain.model.enums.CategoriaEnum;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.ports.GetProdutoInboundPort;
import br.com.fiap.techchallenge.ports.GetProdutoOutboundPort;

import java.util.List;

public class GetProdutoUseCase implements GetProdutoInboundPort {

    private final GetProdutoOutboundPort port;

    public GetProdutoUseCase(GetProdutoOutboundPort port) {
        this.port = port;
    }

    @Override
    public List<ProdutoDTO> pegaProdutosPorCategoria(String categoria) {
        CategoriaEnum categoriaEnum = CategoriaEnum.fromName(categoria);
        return this.port.pegaProdutosPorCategoria(categoriaEnum);
    }
}
