package br.com.fiap.techchallenge.naousar.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Objeto que representa um item do pedido dentro do sistema")
public class ProdutoPedidoDTO {

    @Schema(description = "Produto escolhido pelo cliente")
    private ProdutoDTO produto;

    @Schema(description = "Valor total do produto")
    private BigDecimal valorTotal;

    @Schema(description = "Quantidade total do mesmo produto")
    private BigInteger quantidade;

    @Schema(description = "Identificados do Produto Pedido")
    private Long id;

}