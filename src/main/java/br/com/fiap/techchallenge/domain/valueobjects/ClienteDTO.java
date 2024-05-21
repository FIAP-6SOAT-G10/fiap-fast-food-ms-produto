package br.com.fiap.techchallenge.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Objeto que representa um cliente dentro do sistema")
public class ClienteDTO implements Serializable {

    @Schema(description = "Campo identificador do Cliente")
    private Long id;

    @Schema(description = "Campo identificador único de pessoas fisicas no Brasil", example = "12345678900")
    @NotNull(message = "O cpf é obrigatório")
    private String cpf;

    @Schema(description = "Campo que identifica o nome do cliente", example = "John Doo")
    @NotNull(message = "O nome é obrigatório")
    private String nome;

    @Schema(description = "Campo que identifica o email do cliente", example = "email@email.com")
    @NotNull(message = "O email é obrigatório")
    private String email;

}