package br.com.fiap.techchallenge.infra.mapper.categoria;

import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoriaMapper {

    default CategoriaEnum fromCategoriaToCategoriaEnum(CategoriaEntity categoriaEntity) {
        return CategoriaEnum.fromName(categoriaEntity.getNome());
    }

    List<Categoria> fromListEntityToListDTO(List<CategoriaEntity> categoriaEntities);

}