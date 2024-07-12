package br.com.fiap.techchallenge.infra.mapper.cliente;

import br.com.fiap.techchallenge.domain.entities.cliente.Cliente;
import br.com.fiap.techchallenge.infra.persistence.entities.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {

    ClienteEntity toEntity(Cliente cliente);

    Cliente fromEntityToDomain(ClienteEntity clienteEntity);

    List<Cliente> fromListEntityToListDTO(List<ClienteEntity> clienteEntities);

    List<ClienteEntity> fromListDTOToListEntity(List<Cliente> clientes);

}