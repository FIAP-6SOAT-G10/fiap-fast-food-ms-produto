package br.com.fiap.techchallenge.infra.mapper;

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

    ProdutoPedido fromEntityToDomain(ProdutoPedidoEntity produtoPedido);

    List<ProdutoPedido> fromListEntityToListDomain(List<ProdutoPedidoEntity> produtosPedido);

}
