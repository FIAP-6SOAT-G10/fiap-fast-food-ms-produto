package br.com.fiap.techchallenge.adapters.produto;

import br.com.fiap.techchallenge.domain.entities.Categoria;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.repositories.CategoriaRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.produto.PatchProdutoOutboundPort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class PatchProdutoAdapter implements PatchProdutoOutboundPort {

    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public PatchProdutoAdapter(ProdutoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Produto atualizarDadosProduto(Long id, JsonPatch patch) {
        Optional<Produto> produtoOptional = repository.findById(id);
        if (produtoOptional.isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode patched = patch.apply(objectMapper.convertValue(produtoOptional.get(), JsonNode.class));
                Produto produto = objectMapper.treeToValue(patched, Produto.class);

                Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
                categoriaOptional.ifPresent(produto::setCategoria);

                return repository.saveAndFlush(produto);
            } catch (JsonPatchException jsonPatchException) {
                log.error("Erro ao atualizar o registro no banco de dados", jsonPatchException);
                throw new ProdutoException(ErrosEnum.PRODUTO_FALHA_DURANTE_ATUALIZACAO);
            } catch (Exception e) {
                log.error("Falha genérica.", e);
                throw new ProdutoException(ErrosEnum.PRODUTO_FALHA_GENERICA);
            }
        } else {
            throw new ProdutoException(ErrosEnum.PRODUTO_NAO_ENCONTRADO);
        }
    }
}
