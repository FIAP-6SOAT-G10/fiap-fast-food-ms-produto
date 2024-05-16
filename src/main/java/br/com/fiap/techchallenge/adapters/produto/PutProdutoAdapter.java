package br.com.fiap.techchallenge.adapters.produto;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.model.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.produto.PutProdutoOutboundPort;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class PutProdutoAdapter implements PutProdutoOutboundPort {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public PutProdutoAdapter(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Override
    public Produto atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        Produto novoProduto = produtoMapper.fromDTOToEntity(produtoDTO);

        log.info("Atualizando produto com novos dados");
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto antigoProduto = produtoOptional.get();

            preencherComDadosNovos(antigoProduto, novoProduto);
            return produtoRepository.saveAndFlush(antigoProduto);
        } else {
            log.error("O identificador informado não existe no banco de dados.");
            throw new ProdutoException(ErrosEnum.PRODUTO_CODIGO_IDENTIFICADOR_INVALIDO);
        }
    }

    private void preencherComDadosNovos(Produto antigoProduto, Produto novoProduto) {
        antigoProduto.setNome(novoProduto.getNome());
        antigoProduto.setDescricao(novoProduto.getDescricao());
        antigoProduto.setCategoria(novoProduto.getCategoria());
        antigoProduto.setPreco(novoProduto.getPreco());
        antigoProduto.setImagem(novoProduto.getImagem());
    }
}
