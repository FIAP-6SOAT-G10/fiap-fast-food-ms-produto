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
@Schema(name = "Categoria", description = "Essa classe deve ser utilizada para enviar as informações para o cadastro de uma nova categoria no sistema")
public class CategoriaDTO {

    @Schema(description = "O nome da categoria que será criado.", example = "Bebida")
    private String nome;

    @Schema(description = "A descrição que será exibida da categoria.", example = "Produtos orgânicos e sem glúten.")
    private String descricao;

}