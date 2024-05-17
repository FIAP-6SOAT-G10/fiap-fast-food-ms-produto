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
public class StatusPedidoDTO {
    @Schema(description = "Campo identificador único de status do pedido", example = "1")
    private Long id;

    @Schema(description = "Campo que informa o nome do status do pedido", example = "Em Preparo")
    private String nome;
}