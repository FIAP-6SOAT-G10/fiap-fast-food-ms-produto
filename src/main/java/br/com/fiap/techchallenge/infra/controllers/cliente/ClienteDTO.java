package br.com.fiap.techchallenge.infra.controllers.cliente;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Cliente", description = "Objeto que representa um cliente dentro do sistema")
public class ClienteDTO{

        @Schema(description = "O identificador do cliente.", example = "1")
        Long id;

        @Schema(description = "O cpf do cliente que será criado.", example = "123.123.123-12")
        String cpf;

        @Schema(description = "O nome do cliente que será criado.", example = "João da Silva")
        String nome;

        @Schema(description = "O e-mail do cliente que será criado.", example = "teste@teste.com.br")
        String email;

        // Construtor privado que aceita apenas o CPF
        private ClienteDTO(String cpf) {
                this.cpf = cpf;
        }

        // Método estático para criar uma instância de ClienteDTO apenas com o CPF
        public static ClienteDTO withCpf(String cpf) {
                return new ClienteDTO(cpf);
        }
}
