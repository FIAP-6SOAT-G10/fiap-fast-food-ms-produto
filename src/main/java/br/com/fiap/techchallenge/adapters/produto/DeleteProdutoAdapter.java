package br.com.fiap.techchallenge.adapters.produto;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.produto.DeleteProdutoOutboundPort;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class DeleteProdutoAdapter implements DeleteProdutoOutboundPort {

    private final ProdutoRepository produtoRepository;

    public DeleteProdutoAdapter(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto deletarProduto(Long id) {
        log.info("Deletando produto");

        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isEmpty()) {
            log.error("O identificador informado não existe no banco de dados.");
            throw new ProdutoException(ErrosEnum.PRODUTO_NAO_ENCONTRADO);
        } else {
            produtoRepository.deleteById(id);
            return produtoOptional.get();
        }
    }
}
