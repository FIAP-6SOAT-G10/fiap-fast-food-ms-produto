package br.com.fiap.techchallenge.domain.model.mapper.pedido;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoMapper {}
