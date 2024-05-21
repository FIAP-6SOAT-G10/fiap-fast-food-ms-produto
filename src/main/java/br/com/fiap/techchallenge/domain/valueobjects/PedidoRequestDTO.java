package br.com.fiap.techchallenge.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonPropertyOrder(
        {
            "cliente",
            "items"
        })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {

    private String cliente;
    private ItemDTO items;

}
