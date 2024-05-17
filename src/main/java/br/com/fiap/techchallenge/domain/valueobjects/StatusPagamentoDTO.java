package br.com.fiap.techchallenge.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusPagamentoDTO {
    @Schema(description = "Campo identificador único de status de pagamento", example = "1")
    private Long id;

    @Schema(description = "Campo que informa o nome do status de pagamento", example = "Pago")
    private String nome;
}