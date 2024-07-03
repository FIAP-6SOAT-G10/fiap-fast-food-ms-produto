package br.com.fiap.techchallenge.naousar.domain.usecases.produto;

import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.naousar.ports.produto.DeleteProdutoInboundPort;
import br.com.fiap.techchallenge.naousar.ports.produto.DeleteProdutoOutboundPort;

import java.util.regex.Pattern;

public class DeleteProdutoUseCase implements DeleteProdutoInboundPort {

    private final DeleteProdutoOutboundPort port;

    public DeleteProdutoUseCase(DeleteProdutoOutboundPort port) {
        this.port = port;
    }

    @Override
    public ProdutoEntity deletarProduto(String id) {
        validar(id);
        return this.port.deletarProduto(Long.valueOf(id));
    }

    private void validar(String id) {
        Pattern pattern = Pattern.compile("[^\\d+]");
        if (pattern.matcher(id).find()) {
            throw new ProdutoException(ErrosEnum.PRODUTO_CODIGO_IDENTIFICADOR_INVALIDO);
        }
    }
}
