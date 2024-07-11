package br.com.fiap.techchallenge.infra.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Cliente", description = "Objeto que representa um cliente dentro do sistema")
public record ClienteDTO(

        @Schema(description = "O identificador do cliente.", example = "1")
        Long id,

        @Schema(description = "O cpf do cliente que será criado.", example = "123.123.123-12")
        String cpf,

        @Schema(description = "O nome do cliente que será criado.", example = "João da Silva")
        String nome,

        @Schema(description = "O e-mail do cliente que será criado.", example = "teste@teste.com.br")
        String email

) {
}
