package br.com.fiap.techchallenge.domain.valueobjects;

import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.entities.ProdutoPedido;
import br.com.fiap.techchallenge.domain.entities.StatusPagamento;
import br.com.fiap.techchallenge.domain.entities.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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