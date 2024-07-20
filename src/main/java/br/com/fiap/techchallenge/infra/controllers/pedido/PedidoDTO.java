package br.com.fiap.techchallenge.infra.controllers.pedido;

import br.com.fiap.techchallenge.domain.entities.pagamento.StatusPagamento;
import br.com.fiap.techchallenge.domain.entities.pedido.ProdutoPedido;
import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedido;
import br.com.fiap.techchallenge.infra.controllers.cliente.ClienteDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Objeto que representa um pedido dentro do sistema")
public class PedidoDTO {

    @Schema(description = "Campo identificador único de pedido", example = "12345678900")
    private Long id;

    @Schema(description = "Campo identificador único de do cliente que solicitou o pedido", example = "12345678900")
    private ClienteDTO cliente;

    @Schema(description = "Campo que informa o status do pedido", example = "Em Preparo")
    private StatusPedido status;

    @Schema(description = "Campo que informa o valor totla do pedido", example = "89.25")
    private BigDecimal valor;

    @Schema(description = "Data de criação do pedido", example = "02/05/2024")
    private LocalDateTime dataCriacao;

    @Schema(description = "Data de finalização do pedido", example = "02/05/2024")
    private LocalDateTime dataFinalizacao;

    @Schema(description = "Data de cancelamento do pedido", example = "02/05/2024")
    private LocalDateTime dataCancelamento;

    @Schema(description = "Campo que informa o status de pagamento do pedido", example = "Pago")
    private StatusPagamento statusPagamento;

    @Schema(description = "Campo que informa os produtos do pedido")
    private List<ProdutoPedido> produtos;

    @Schema(description = "Campo que informa os itens do pedido")
    private ItemDTO items;

}
