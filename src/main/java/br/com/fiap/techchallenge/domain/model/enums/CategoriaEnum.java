package br.com.fiap.techchallenge.domain.model.enums;

import br.com.fiap.techchallenge.infra.exception.CategoriaException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public enum CategoriaEnum {
    LANCHE(1, "LANCHE"),
    BEBIDA(2, "BEBIDA"),
    ACOMPANHAMENTO(3, "ACOMPANHAMENTO"),
    SOBREMESA(4, "SOBREMESA");

    private final Integer idCategoria;
    private final String nome;

    @JsonCreator
    public static CategoriaEnum fromName(String name) throws CategoriaException {
        Predicate<CategoriaEnum> byName = v -> v.getNome().equalsIgnoreCase(name);
        return Arrays.stream(values()).filter(byName).findAny().orElseThrow(() -> new CategoriaException(ErrosEnum.CATEGORIA_INVALIDA, "Categoria não encontrada."));
    }

    public static CategoriaEnum fromIdCategoria(Long id) {
        for (CategoriaEnum categoria : CategoriaEnum.values()) {
            if (categoria.idCategoria.equals(id.intValue())) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Invalid codigo: " + id);
    }

}