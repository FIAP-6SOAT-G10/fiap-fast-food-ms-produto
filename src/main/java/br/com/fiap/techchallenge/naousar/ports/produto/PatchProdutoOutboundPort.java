package br.com.fiap.techchallenge.naousar.ports.produto;

import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import com.github.fge.jsonpatch.JsonPatch;

public interface PatchProdutoOutboundPort {

    ProdutoEntity atualizarDadosProduto(Long id, JsonPatch patch);

}
