package br.com.fiap.techchallenge.domain.model.mapper;

import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.domain.entities.Cliente;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {

    Cliente toEntity(ClienteDTO clienteDTO);

    ClienteDTO toDTO(Cliente cliente);

    List<ClienteDTO> fromListEntityToListDTO(List<Cliente> clientes);

    List<Cliente> fromListDTOToListEntity(List<ClienteDTO> clientes);

}