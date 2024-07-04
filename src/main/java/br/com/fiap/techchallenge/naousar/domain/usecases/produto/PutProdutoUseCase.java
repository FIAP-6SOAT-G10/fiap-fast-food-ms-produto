package br.com.fiap.techchallenge.naousar.domain.usecases.produto;

import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.naousar.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.naousar.ports.produto.PutProdutoInboundPort;
import br.com.fiap.techchallenge.naousar.ports.produto.PutProdutoOutboundPort;

import java.util.Arrays;
import java.util.regex.Pattern;

public class PutProdutoUseCase implements PutProdutoInboundPort {

    private final PutProdutoOutboundPort port;

    public PutProdutoUseCase(PutProdutoOutboundPort port) {
        this.port = port;
    }

    @Override
    public ProdutoEntity atualizarProduto(String id, ProdutoDTO produtoDTO) {
        validar(id, produtoDTO);
        return this.port.atualizarProduto(Long.valueOf(id), produtoDTO);
    }

    private void validar(String id, ProdutoDTO produtoDTO) {
        Pattern pattern = Pattern.compile("[^\\d+]");
        if (pattern.matcher(id).find()) {
            throw new ProdutoException(ErrosEnum.PRODUTO_CODIGO_IDENTIFICADOR_INVALIDO);
        }

        if (produtoDTO.getNome() == null || produtoDTO.getNome().isEmpty()) {
            throw new ProdutoException(ErrosEnum.PRODUTO_NOME_OBRIGATORIO);
        }

        if (produtoDTO.getDescricao() == null || produtoDTO.getDescricao().isEmpty()) {
            throw new ProdutoException(ErrosEnum.PRODUTO_DESCRICAO_OBRIGATORIO);
        }

        if (produtoDTO.getCategoria() == null || Arrays.stream(CategoriaEnum.values()).noneMatch(categorias -> categorias.getNome().equalsIgnoreCase(produtoDTO.getCategoria().getNome()))) {
            throw new ProdutoException(ErrosEnum.CATEGORIA_INVALIDA);
        }

        if (produtoDTO.getPreco() == null || produtoDTO.getPreco().doubleValue() <= 0) {
            throw new ProdutoException(ErrosEnum.PRODUTO_PRECO_OBRIGATORIO);
        }

        if (produtoDTO.getImagem() == null || produtoDTO.getImagem().isEmpty()) {
            throw new ProdutoException(ErrosEnum.PRODUTO_IMAGEM_OBRIGATORIO);
        }
    }
}
