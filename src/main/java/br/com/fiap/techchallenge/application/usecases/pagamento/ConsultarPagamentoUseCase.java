package br.com.fiap.techchallenge.application.usecases.pagamento;

import br.com.fiap.techchallenge.application.usecases.pedido.PatchPedidoUseCase;
import br.com.fiap.techchallenge.domain.entities.pagamento.PagamentoResponseDTO;
import br.com.fiap.techchallenge.infra.exception.MercadoPagoException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ConsultarPagamentoUseCase {

    private final PatchPedidoUseCase patchPedidoUseCase;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${mercadopago.access_token}")
    private String mercadoPagoAccessToken;

    public ConsultarPagamentoUseCase(PatchPedidoUseCase patchPedidoUseCase) {
        this.patchPedidoUseCase = patchPedidoUseCase;
    }

        public PagamentoResponseDTO consultarPagamento(Long paymentId) {
            try {
                MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);
                PaymentClient paymentClient = new PaymentClient();
                Payment payment = paymentClient.get(paymentId);

                PagamentoResponseDTO pagamento = new PagamentoResponseDTO(
                        payment.getId(),
                        "approved",
                        payment.getStatusDetail(),
                        payment.getExternalReference());

                ObjectMapper objectMapper = new ObjectMapper();
                JsonPointer pointer = new JsonPointer("/statusPagamento");

                JsonNode nodeDTO = null;
                if (pagamento.getStatus().equals("pending") || pagamento.getStatus().equals("in_process")) {
                    nodeDTO = objectMapper.convertValue("pendente", JsonNode.class);
                } else if (pagamento.getStatus().equals("approved")) {
                    nodeDTO = objectMapper.convertValue("pago", JsonNode.class);
                } else {
                    nodeDTO = objectMapper.convertValue("recusado", JsonNode.class);
                }

                List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));
                JsonPatch jsonPatch = new JsonPatch(operations);
                patchPedidoUseCase.atualizarPagamentoDoPedido(pagamento.getExternalId(), jsonPatch);

                return pagamento;
            } catch (MPApiException apiException) {
                throw new MercadoPagoException(apiException.getApiResponse().getContent());
            } catch (MPException exception) {
                throw new MercadoPagoException(exception.getMessage());
            } catch (JsonPointerException e) {
                throw new RuntimeException(e);
            }
        }
}
