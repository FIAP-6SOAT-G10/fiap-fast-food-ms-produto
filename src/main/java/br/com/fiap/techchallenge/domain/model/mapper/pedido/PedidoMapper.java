package br.com.fiap.techchallenge.domain.model.mapper.pedido;

import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoMapper {

    PedidoDTO toDTO(Pedido pedido);

}
