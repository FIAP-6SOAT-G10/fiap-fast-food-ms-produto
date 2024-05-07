package br.com.fiap.techchallenge.domain.valueobjects;

import br.com.fiap.techchallenge.domain.model.enums.CategoriaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Produto", description = "Essa classe deve ser utilizada para enviar as informações para cadastro de um novo produto no sistema")
public class ProdutoDTO {

    @Schema(description = "O nome do produto que será criado.", example = "Magnífico Bacon")
    private String nome;

    @Schema(description = "A descrição que será exibida do produto.", example = "Magnífico hamburguer feito com pão crocante, hamburguer artesanal smash, salada fresca, molho da casa e queijo tipo gruyere.")
    private String descricao;

    @Schema(description = "A categoria que o produto ocupará dentro do sistema.", example = "LANCHE", allowableValues = {
            "LANCHE", "BEBIDA", "ACOMPANHAMENTO", "SOBREMESA"
    })
    private CategoriaEnum categoria;

    @Schema(description = "O preço que será ofertado para o produto.", example = "19.90")
    private BigDecimal preco;

    @Schema(description = "A imagem que será exibida aos clientes.")
    private String imagem;
}
