package br.com.fiap.techchallenge.application.usecases.cliente;

import br.com.fiap.techchallenge.application.gateways.IClienteRepository;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.cliente.Cliente;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.regex.Pattern;

public class AtualizarClienteParcialUseCase {

    private final IClienteRepository clienteRepository;

    public AtualizarClienteParcialUseCase(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente atualizarDadosCliente(String id, JsonPatch patch) {
        validarDados(id, patch);
        return this.clienteRepository.atualizarDadosCliente(Long.valueOf(id), patch);
    }

    private void validarDados(String id, JsonPatch patch) {
        Pattern pattern = Pattern.compile("[^\\d+]");
        if (pattern.matcher(id).find()) {
            throw new ProdutoException(ErrosEnum.CLIENTE_CODIGO_IDENTIFICADOR_INVALIDO);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode request = objectMapper.convertValue(patch, JsonNode.class);

        for (int index = 0; index < request.size(); index++) {

            JsonNode parent = request.get(index);
            if (parent.has("path")) {
                JsonNode path = parent.get("path");

                verificarCpf(path, parent);
                verificarNome(path, parent);
                verificarEmail(path, parent);
            }
        }
    }

    private void verificarCpf(JsonNode path, JsonNode parent) {
        if (path.asText().equalsIgnoreCase("/cpf")) {
            String cpfContent = parent.get("value").asText();
            if (cpfContent == null || cpfContent.isEmpty()) {
                throw new ProdutoException(ErrosEnum.CLIENTE_CPF_INVALIDO);
            }
        }
    }

    private void verificarNome(JsonNode path, JsonNode parent) {
        if (path.asText().equalsIgnoreCase("/nome")) {
            String nomeContent = parent.get("value").asText();
            if (nomeContent == null || nomeContent.isEmpty()) {
                throw new ProdutoException(ErrosEnum.CLIENTE_NOME_OBRIGATORIO);
            }
        }
    }

    private void verificarEmail(JsonNode path, JsonNode parent) {
        if (path.asText().equalsIgnoreCase("/email")) {
            String emailContent = parent.get("value").asText();
            if (emailContent == null || emailContent.isEmpty()) {
                throw new ProdutoException(ErrosEnum.CLIENTE_EMAIL_OBRIGATORIO);
            }
        }
    }
}
