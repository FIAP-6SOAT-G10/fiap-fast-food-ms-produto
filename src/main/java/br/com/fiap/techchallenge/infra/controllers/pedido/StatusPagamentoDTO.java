package br.com.fiap.techchallenge.infra.controllers.pedido;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Objeto que representa um status de pagamento do pedido dentro do sistema")
public class StatusPagamentoDTO {

    @Schema(description = "Campo que informa o status de pagamento do pedido", example = "pago")
    private String status;

}
