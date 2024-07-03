package br.com.fiap.techchallenge.naousar.domain.valueobjects;

import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Produto", description = "Objeto que representa um produto dentro do sistema")
public class ProdutoDTO {

    @Schema(description = "O identificador do produto.", example = "1")
    private Long id;

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