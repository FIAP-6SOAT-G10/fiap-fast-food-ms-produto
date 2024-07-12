package br.com.fiap.techchallenge.infra.controllers;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    private List<ItemPedidoDTO> lanches = new ArrayList<>();
    private List<ItemPedidoDTO> acompanhamento = new ArrayList<>();
    private List<ItemPedidoDTO> bebida = new ArrayList<>();
    private List<ItemPedidoDTO> sobremesa = new ArrayList<>();

}