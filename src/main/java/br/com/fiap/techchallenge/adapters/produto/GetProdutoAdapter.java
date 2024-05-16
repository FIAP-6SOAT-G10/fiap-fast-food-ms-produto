package br.com.fiap.techchallenge.adapters.produto;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.enums.CategoriaEnum;
import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.model.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.produto.GetProdutoOutboundPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
public class GetProdutoAdapter implements GetProdutoOutboundPort {
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public GetProdutoAdapter(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Override
    public List<ProdutoDTO> listarProdutos(Integer page, Integer size, String nome, String descricao, BigDecimal preco) {
        List<Produto> listaProduto = new ArrayList<>();
        Predicate<Produto> byNomeOrDescricaoOrPreco = produto -> {
            Boolean hasSameNome = nome == null || produto.getNome().equals(nome);
            Boolean hasSameDescricao = descricao == null || produto.getDescricao().equals(descricao);
            Boolean hasSamePreco = preco == null || produto.getPreco().equals(preco);

            return hasSameNome && hasSameDescricao && hasSamePreco;
        };

        if (nome != null || descricao != null || preco != null) {
            produtoRepository.findByNomeOrDescricaoOrPreco(nome, descricao, preco).ifPresent(produtoList -> {
                List<Produto> filteredProduto = produtoList.stream().filter(byNomeOrDescricaoOrPreco).toList();
                listaProduto.addAll(filteredProduto);
            });
        } else {
            PageRequest pageable = PageRequest.of(page, size);
            listaProduto.addAll(produtoRepository.findAll(pageable).toList());
        }

        return produtoMapper.fromListEntityToListDTO(listaProduto);
    }

    @Override
    public List<Produto> pegaProdutosPorCategoria(CategoriaEnum categoriaEnum) {
        log.info("Consultando banco de dados para buscar os produtos da categoria.");
        Optional<List<Produto>> optionalProdutos = produtoRepository.findAllByCategoriaId(categoriaEnum.getIdCategoria());
        if (optionalProdutos.isEmpty()) {
            throw new ProdutoException(ErrosEnum.PRODUTO_CATEGORIA_NAO_ENCONTRADO);
        }
        return optionalProdutos.get();
    }
}
