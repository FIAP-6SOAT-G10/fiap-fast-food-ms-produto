package br.com.fiap.techchallenge.application.usecases.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.regex.Pattern;

public class AtualizarProdutoParcialUseCase {

    private static final String VALUE_PARAM = "value";
    private static final String REGEX = "[^\\d+]";

    private final IProdutoRepository produtoRepository;

    public AtualizarProdutoParcialUseCase(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto atualizarDadosProduto(String id, JsonPatch patch) {
        validarDados(id, patch);
        return this.produtoRepository.atualizarDadosProduto(Long.valueOf(id), patch);
    }

    private void validarDados(String id, JsonPatch patch) {
        Pattern pattern = Pattern.compile(REGEX);
        if (pattern.matcher(id).find()) {
            throw new ProdutoException(ErrosEnum.PRODUTO_CODIGO_IDENTIFICADOR_INVALIDO);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode request = objectMapper.convertValue(patch, JsonNode.class);

        for (int index = 0 ; index < request.size() ; index++) {

            JsonNode parent = request.get(index);
            if (parent.has("path")) {
                JsonNode path = parent.get("path");

                verificarNome(path, parent);
                verificarDescricao(path, parent);
                verificarCategoria(path, parent);
                verificarPreco(path, parent);
                verificarImagem(path, parent);
            }

        }
    }

    private void verificarNome(JsonNode path, JsonNode parent) {
        if (path.asText().equalsIgnoreCase("/nome")) {
            String nomeContent = parent.get(VALUE_PARAM).asText();
            if (nomeContent == null || nomeContent.isEmpty()) {
                throw new ProdutoException(ErrosEnum.PRODUTO_NOME_OBRIGATORIO);
            }
        }
    }

    private void verificarDescricao(JsonNode path, JsonNode parent) {
        if (path.asText().equalsIgnoreCase("/descricao")) {
            String descricaoContent = parent.get(VALUE_PARAM).asText();
            if (descricaoContent == null || descricaoContent.isEmpty()) {
                throw new ProdutoException(ErrosEnum.PRODUTO_DESCRICAO_OBRIGATORIO);
            }
        }
    }

    private void verificarCategoria(JsonNode path, JsonNode parent) {
        if (path.asText().equalsIgnoreCase("/categoria")) {
            String categoriaContent = parent.get(VALUE_PARAM).asText();
            if (categoriaContent == null || categoriaContent.isEmpty()) {
                throw new ProdutoException(ErrosEnum.CATEGORIA_INVALIDA);
            }
        }
    }

    private void verificarPreco(JsonNode path, JsonNode parent) {
        if (path.asText().equalsIgnoreCase("/preco")) {
            String precoContent = parent.get(VALUE_PARAM).asText();
            if (precoContent == null || precoContent.isEmpty()) {
                throw new ProdutoException(ErrosEnum.PRODUTO_PRECO_OBRIGATORIO);
            }
        }
    }

    private void verificarImagem(JsonNode path, JsonNode parent) {
        if (path.asText().equalsIgnoreCase("/imagem")) {
            String imagemContent = parent.get(VALUE_PARAM).asText();
            if (imagemContent == null || imagemContent.isEmpty()) {
                throw new ProdutoException(ErrosEnum.PRODUTO_IMAGEM_OBRIGATORIO);
            }
        }
    }

}
