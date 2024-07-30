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

        Cliente cliente = new Cliente();
        cliente.setId(clienteEntity.getId());
        cliente.setCpf(clienteEntity.getCpf());
        cliente.setNome(clienteEntity.getNome());
        cliente.setEmail(clienteEntity.getEmail());

        return cliente;
    }

    public Cliente fromDTOToDomain(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteDTO.getId());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
//        cliente.setPedidos(PedidoMapper);

        return cliente;
    }

    public List<Cliente> fromListEntityToListDomain(List<ClienteEntity> clientes) {
        return clientes.stream().map(this::fromEntityToDomain).toList();
    }

    public List<ClienteEntity> fromListDomainToListEntity(List<Cliente> clientes) {
        return clientes.stream().map(this::fromDomainToEntity).toList();
    }
}
