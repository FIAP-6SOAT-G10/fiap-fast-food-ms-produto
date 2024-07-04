package br.com.fiap.techchallenge.infra.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaDTO {
    @Schema(description = "O nome da categoria que será criado.", example = "Bebida")
    private String nome;

    @Schema(description = "A descrição que será exibida da categoria.", example = "Produtos orgânicos e sem glúten.")
    private String descricao;

    public CategoriaDTO(String nome) {
        this.nome = nome;
    }

    public CategoriaDTO(String nome, String descricao) {
        this(nome);
        this.descricao = descricao;
    }
}
