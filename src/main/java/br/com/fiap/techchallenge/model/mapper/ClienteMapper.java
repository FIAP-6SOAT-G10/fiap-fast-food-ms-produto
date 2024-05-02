package br.com.fiap.techchallenge.model.mapper;

import br.com.fiap.techchallenge.model.dto.ClienteDTO;
import br.com.fiap.techchallenge.model.entity.Cliente;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);
    Cliente toEntity(ClienteDTO clienteDTO);

    ClienteDTO toDTO(Cliente cliente);

}