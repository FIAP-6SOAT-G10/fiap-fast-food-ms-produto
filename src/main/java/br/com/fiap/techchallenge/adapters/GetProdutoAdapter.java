package br.com.fiap.techchallenge.adapters;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.enums.CategoriaEnum;
import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.model.mapper.ProdutoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.GetProdutoOutboundPort;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class GetProdutoAdapter implements GetProdutoOutboundPort {
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public GetProdutoAdapter(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Override
    public List<ProdutoDTO> pegaProdutosPorCategoria(CategoriaEnum categoriaEnum) {
        log.info("Consultando banco de dados para buscar os produtos da categoria.");
        Optional<List<Produto>> optionalProdutos = produtoRepository.findAllByCategoriaId(categoriaEnum.getIdCategoria());
        if (optionalProdutos.isEmpty()) {
            throw new ProdutoException(ErrosEnum.PRODUTO_CATEGORIA_NAO_ENCONTRADO);
        }
        return produtoMapper.fromListEntityToListDTO(optionalProdutos.get());
    }
}
