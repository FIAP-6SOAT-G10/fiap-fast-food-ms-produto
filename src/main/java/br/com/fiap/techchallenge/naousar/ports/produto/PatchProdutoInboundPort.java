package br.com.fiap.techchallenge.naousar.ports.produto;

import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import com.github.fge.jsonpatch.JsonPatch;

public interface PatchProdutoInboundPort {

    ProdutoEntity atualizarDadosProduto(String id, JsonPatch patch);

}