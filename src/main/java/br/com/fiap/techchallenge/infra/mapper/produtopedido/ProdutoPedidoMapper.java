package br.com.fiap.techchallenge.infra.mapper.produtopedido;


import br.com.fiap.techchallenge.domain.entities.pedido.ProdutoPedido;
import br.com.fiap.techchallenge.infra.mapper.categoria.CategoriaMapper;
import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoPedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
        CategoriaMapper.class
})
public interface ProdutoPedidoMapper {

    ProdutoPedido fromEntityToDTO(ProdutoPedidoEntity produtoPedido);

    List<ProdutoPedido> fromListEntityToListDTO(List<ProdutoPedidoEntity> produtosPedido);

    List<ProdutoPedidoEntity> fromDomainToEntity(List<ProdutoPedido> produtosPedido);

}
