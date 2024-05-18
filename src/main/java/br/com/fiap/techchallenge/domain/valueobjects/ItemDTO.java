package br.com.fiap.techchallenge.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonPropertyOrder(
        {
            "lanches",
            "acompanhamento",
            "bebida",
            "sobremesa"
        })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    List<ItemPedidoDTO> lanches;
    List<ItemPedidoDTO> acompanhamento;
    List<ItemPedidoDTO> bebida;
    List<ItemPedidoDTO> sobremesa;

}