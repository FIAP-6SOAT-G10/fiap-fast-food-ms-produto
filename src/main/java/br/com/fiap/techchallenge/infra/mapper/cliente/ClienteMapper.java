package br.com.fiap.techchallenge.infra.mapper.cliente;

import br.com.fiap.techchallenge.infra.persistence.entities.Cliente;
import br.com.fiap.techchallenge.naousar.domain.valueobjects.ClienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {

    Cliente toEntity(ClienteDTO clienteDTO);

    ClienteDTO toDTO(Cliente cliente);

    List<ClienteDTO> fromListEntityToListDTO(List<Cliente> clientes);

    List<Cliente> fromListDTOToListEntity(List<ClienteDTO> clientes);

}