package br.com.fiap.techchallenge.domain.valueobjects;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Categoria", description = "Objeto que representa uma categoria de produtos dentro do sistema")
public class CategoriaDTO {

    @Schema(description = "O nome da categoria que será criado.", example = "Bebida")
    private String nome;

    @Schema(description = "A descrição que será exibida da categoria.", example = "Produtos orgânicos e sem glúten.")
    private String descricao;

}