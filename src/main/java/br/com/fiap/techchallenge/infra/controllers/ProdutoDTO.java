package br.com.fiap.techchallenge.infra.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Produto", description = "Objeto que representa um produto dentro do sistema")
public record ProdutoDTO(
        @Schema(description = "O identificador do produto.", example = "1")
        Long id,

        @Schema(description = "O nome do produto que será criado.", example = "Magnífico Bacon")
        String nome,

        @Schema(description = "A descrição que será exibida do produto.", example = "Magnífico hamburguer feito com pão crocante, hamburguer artesanal smash, salada fresca, molho da casa e queijo tipo gruyere.")
        String descricao,

        @Schema(description = "A categoria que o produto ocupará dentro do sistema.", example = "LANCHE", allowableValues = {
            "LANCHE", "BEBIDA", "ACOMPANHAMENTO", "SOBREMESA"
        })
        CategoriaDTO categoria,

        @Schema(description = "O preço que será ofertado para o produto.", example = "19.90")
        BigDecimal preco,

        @Schema(description = "A imagem que será exibida aos clientes.")
        String imagem
) {
}
