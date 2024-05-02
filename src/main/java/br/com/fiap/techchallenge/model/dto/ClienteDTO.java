package br.com.fiap.techchallenge.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(description = "")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDTO implements Serializable {

    @Schema(description = "Campo identificador único de pessoas fisicas no Brasil" , example = "12345678900")
    @NotNull(message = "O cpf é obrigatório")
    private String cpf;

    @Schema(description = "Campo identificador único de pessoas fisicas no Brasil" , example = "12345678900")
    @NotNull(message = "O nome é obrigatório")
    private String nome;

    @Schema(description = "Campo identificador único de pessoas fisicas no Brasil" , example = "12345678900")
    @NotNull(message = "O email é obrigatório")
    private String email;
}