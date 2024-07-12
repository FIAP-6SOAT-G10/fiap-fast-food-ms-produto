package br.com.fiap.techchallenge.infra.controllers.categoria;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Categoria", description = "Objeto que representa um produto dentro do sistema")
public record CategoriaDTO (
        @Schema(description = "O nome da categoria que será criado.", example = "Bebida")
        String nome,

        @Schema(description = "A descrição que será exibida da categoria.", example = "Produtos orgânicos e sem glúten.")
        String descricao
) {
}
