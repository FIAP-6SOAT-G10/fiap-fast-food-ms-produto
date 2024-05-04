package br.com.fiap.techchallenge.model.dto;

import br.com.fiap.techchallenge.model.entity.Cliente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(description = "")
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDTO implements Serializable {

    @Schema(description = "Campo identificador único de pessoas fisicas no Brasil", example = "12345678900")
    @NotNull(message = "O cpf é obrigatório")
    private String cpf;

    @Schema(description = "Campo que identifica o nome do cliente", example = "12345678900")
    @NotNull(message = "O nome é obrigatório")
    private String nome;

    @Schema(description = "Campo que identifica o email do cliente", example = "email@email.com")
    @NotNull(message = "O email é obrigatório")
    private String email;

}