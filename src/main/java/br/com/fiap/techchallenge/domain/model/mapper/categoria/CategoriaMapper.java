package br.com.fiap.techchallenge.domain.model.mapper.categoria;

import br.com.fiap.techchallenge.domain.entities.Categoria;
import br.com.fiap.techchallenge.domain.valueobjects.CategoriaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoriaMapper {

    List<CategoriaDTO> fromListEntityToListDTO(List<Categoria> categorias);

}