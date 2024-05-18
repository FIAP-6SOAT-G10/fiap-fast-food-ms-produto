package br.com.fiap.techchallenge.domain.usecases.pedido;

import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.model.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.ports.pedido.PatchPedidoInboundPort;
import br.com.fiap.techchallenge.ports.pedido.PatchPedidoOutboundPort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.regex.Pattern;

public class PatchPedidoUseCase implements PatchPedidoInboundPort {

    private final PatchPedidoOutboundPort port;

    public PatchPedidoUseCase(PatchPedidoOutboundPort port) {
        this.port = port;
    }

    @Override
    public Pedido atualizarStatusDoPedido(String id, JsonPatch patch) {
        validarDados(id, patch);
        return this.port.atualizarStatusDoPedido(Long.valueOf(id), patch);
    }

    private void validarDados(String id, JsonPatch patch) {
        Pattern pattern = Pattern.compile("[^\\d+]");
        if (pattern.matcher(id).find()) {
            throw new PedidoException(ErrosEnum.PEDIDO_CODIGO_IDENTIFICADOR_INVALIDO);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode request = objectMapper.convertValue(patch, JsonNode.class);

        for (int index = 0 ; index < request.size() ; index++) {

            JsonNode parent = request.get(index);
            if (parent.has("path")) {
                JsonNode path = parent.get("path");

                verificarStatus(path, parent);
            }

        }
    }

    private void verificarStatus(JsonNode path, JsonNode parent) {
        if (path.asText().equalsIgnoreCase("/status")) {
            String statusPedidoContent = parent.get("value").asText();
            if (statusPedidoContent == null || statusPedidoContent.isEmpty()) {
                throw new PedidoException(ErrosEnum.PEDIDO_STATUS_OBRIGATORIO);
            }

            StatusPedidoEnum statusPedidoEnum = StatusPedidoEnum.byStatus(statusPedidoContent);
            if (statusPedidoEnum == null) {
                throw new PedidoException(ErrosEnum.PEDIDO_STATUS_NAO_ENCONTRADO);
            }
        }
    }
}
