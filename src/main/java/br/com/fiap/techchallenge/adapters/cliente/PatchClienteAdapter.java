package br.com.fiap.techchallenge.adapters.cliente;

import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.model.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import br.com.fiap.techchallenge.ports.cliente.PatchClienteOutboundPort;

import java.util.Optional;

public class PatchClienteAdapter implements PatchClienteOutboundPort {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper mapper;

    public PatchClienteAdapter(ClienteRepository clienteRepository, ClienteMapper mapper) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    @Override
    public ClienteDTO atualizarClientes(ClienteDTO clienteDTO) {
        Optional<Cliente> existingClienteOpt = clienteRepository.findByCpf(clienteDTO.getCpf());

        if (existingClienteOpt == null || !existingClienteOpt.isPresent()) {
            throw new ClienteException(ErrosEnum.CLIENTE_CPF_NAO_EXISTENTE);
        }

        Cliente existingCliente = existingClienteOpt.get();
        Cliente updatedCliente = new Cliente(
                existingCliente.getId(),
                existingCliente.getCpf(),
                clienteDTO.getNome() == null || clienteDTO.getNome().isEmpty() ? existingCliente.getNome() : clienteDTO.getNome(),
                clienteDTO.getEmail() == null || clienteDTO.getEmail().isEmpty() ? existingCliente.getEmail() : clienteDTO.getEmail()
        );

        updatedCliente = clienteRepository.saveAndFlush(updatedCliente);
        return mapper.toDTO(updatedCliente);
    }
}
