package br.com.fiap.techchallenge.domain.usecases;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.enums.CategoriaEnum;
import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.ports.DeleteProdutoInboundPort;
import br.com.fiap.techchallenge.ports.DeleteProdutoOutboundPort;
import br.com.fiap.techchallenge.ports.PutProdutoInboundPort;
import br.com.fiap.techchallenge.ports.PutProdutoOutboundPort;

import java.util.Arrays;
import java.util.regex.Pattern;

public class DeleteProdutoUseCase implements DeleteProdutoInboundPort {

    private final DeleteProdutoOutboundPort port;

    public DeleteProdutoUseCase(DeleteProdutoOutboundPort port) {
        this.port = port;
    }

    @Override
    public Produto deletarProduto(String id, ProdutoDTO produtoDTO) {
        validar(id, produtoDTO);
        return this.port.deletarProduto(Long.valueOf(id), produtoDTO);
    }

    private void validar(String id, ProdutoDTO produtoDTO) {
        Pattern pattern = Pattern.compile("[^\\d+]");
        if (pattern.matcher(id).find()) {
            throw new ProdutoException(ErrosEnum.PRODUTO_CODIGO_IDENTIFICADOR_INVALIDO);
        }
    }
}
