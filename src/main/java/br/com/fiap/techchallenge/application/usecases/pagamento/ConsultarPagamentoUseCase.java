package br.com.fiap.techchallenge.application.usecases.pagamento;

import br.com.fiap.techchallenge.application.usecases.pedido.PatchPedidoUseCase;
import br.com.fiap.techchallenge.domain.entities.pagamento.PagamentoResponseDTO;
import br.com.fiap.techchallenge.infra.exception.MercadoPagoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
                        String.valueOf(payment.getStatus()),
                        payment.getStatusDetail(),
                        payment.getExternalReference());

                ArrayNode patchArray = objectMapper.createArrayNode();
                ObjectNode operation = objectMapper.createObjectNode();
                operation.put("op", "replace");
                operation.put("path", "/statusPagamento");

                if (pagamento.getStatus().equals("pending") || pagamento.getStatus().equals("in_process")) {
                    operation.put("value", "pendente");
                } else if (pagamento.getStatus().equals("approved")) {
                    operation.put("value", "pago");
                } else {
                    operation.put("value", "recusado");
                }
                patchArray.add(operation);
                JsonPatch jsonPatch = JsonPatch.fromJson(patchArray);
                patchPedidoUseCase.atualizarPagamentoDoPedido(pagamento.getExternalId(), jsonPatch);

                return pagamento;
            } catch (MPApiException apiException) {
                throw new MercadoPagoException(apiException.getApiResponse().getContent());
            } catch (MPException exception) {
                throw new MercadoPagoException(exception.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
