package br.com.fiap.techchallenge.infra.controllers;

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
public class PedidoDTO {

    private String cliente;

    private ItemDTO items;

}
