package br.com.fiap.techchallenge.infra.mapper.cliente;

import br.com.fiap.techchallenge.domain.entities.cliente.Cliente;
import br.com.fiap.techchallenge.domain.entities.pedido.Pedido;
import br.com.fiap.techchallenge.infra.controllers.cliente.ClienteDTO;
import br.com.fiap.techchallenge.infra.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.infra.persistence.entities.ClienteEntity;

import java.util.List;

public class ClienteMapper {

    public ClienteEntity fromDomainToEntity(Cliente cliente) {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(cliente.getId());
        clienteEntity.setCpf(cliente.getCpf());
        clienteEntity.setNome(cliente.getNome());
        clienteEntity.setEmail(cliente.getEmail());

        return clienteEntity;
    }

    public Cliente fromEntityToDomain(ClienteEntity clienteEntity) {
        if (clienteEntity == null) {
            return null;
        }
        return new Cliente(clienteEntity.getId(), clienteEntity.getCpf(), clienteEntity.getNome(), clienteEntity.getEmail());
    }

    public Cliente fromDTOToDomain(ClienteDTO clienteDTO) {
        return new Cliente(clienteDTO.getId(), clienteDTO.getCpf(), clienteDTO.getNome(), clienteDTO.getEmail());
    }

    public List<Cliente> fromListEntityToListDomain(List<ClienteEntity> clientes) {
        return clientes.stream().map(this::fromEntityToDomain).toList();
    }

    public List<ClienteEntity> fromListDomainToListEntity(List<Cliente> clientes) {
        return clientes.stream().map(this::fromDomainToEntity).toList();
    }
}
