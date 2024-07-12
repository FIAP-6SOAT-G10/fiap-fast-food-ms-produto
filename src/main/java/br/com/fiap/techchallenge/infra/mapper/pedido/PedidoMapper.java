package br.com.fiap.techchallenge.infra.mapper.pedido;

import br.com.fiap.techchallenge.domain.entities.pedido.Pedido;
import br.com.fiap.techchallenge.infra.mapper.categoria.CategoriaMapper;
import br.com.fiap.techchallenge.infra.persistence.entities.PedidoEntity;
import br.com.fiap.techchallenge.infra.mapper.produto.ProdutoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
        CategoriaMapper.class,
        ProdutoMapper.class
})
public interface PedidoMapper {

    Pedido fromEntityToDomain(PedidoEntity pedido);

    PedidoEntity toEntity(Pedido pedido);

    List<Pedido> fromListEntityToListDTO(List<PedidoEntity> pedidos);

    List<PedidoEntity> fromListDTOToListEntity(List<Pedido> pedidos);

}
