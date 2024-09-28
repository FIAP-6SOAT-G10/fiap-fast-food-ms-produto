package br.com.fiap.techchallenge.application.usecases.pagamento;

import br.com.fiap.techchallenge.application.usecases.pedido.PatchPedidoUseCase;
import br.com.fiap.techchallenge.domain.entities.pagamento.EventoPagemento;
import br.com.fiap.techchallenge.domain.exceptions.IntegrationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;

import java.util.List;

public class ConsultarPagamentoUseCase {

    private final PatchPedidoUseCase patchPedidoUseCase;

    private final String mercadoPagoAccessToken;

    public ConsultarPagamentoUseCase(PatchPedidoUseCase patchPedidoUseCase, String mercadoPagoAccessToken) {
        this.patchPedidoUseCase = patchPedidoUseCase;
        this.mercadoPagoAccessToken = mercadoPagoAccessToken;
    }

    public EventoPagemento consultarPagamento(EventoPagemento eventoPagemento) {
        try {
            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonPointer pointer = new JsonPointer("/statusPagamento");

            JsonNode nodeDTO = null;
            if (eventoPagemento.getStatus().equals("pending") || eventoPagemento.getStatus().equals("in_process")) {
                nodeDTO = objectMapper.convertValue("pendente", JsonNode.class);
            } else if (eventoPagemento.getStatus().equals("approved")) {
                nodeDTO = objectMapper.convertValue("pago", JsonNode.class);
            } else {
                nodeDTO = objectMapper.convertValue("recusado", JsonNode.class);
            }

            List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));
            JsonPatch jsonPatch = new JsonPatch(operations);
            patchPedidoUseCase.atualizarPagamentoDoPedido(eventoPagemento.getId().toString(), jsonPatch);

            return eventoPagemento;
        } catch (Exception ex) {
            throw new IntegrationException(ex.getMessage());
        }
    }
}
