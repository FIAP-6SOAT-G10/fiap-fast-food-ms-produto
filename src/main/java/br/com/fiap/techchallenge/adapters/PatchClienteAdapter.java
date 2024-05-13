package br.com.fiap.techchallenge.adapters;

import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.model.mapper.ClienteMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import br.com.fiap.techchallenge.ports.PatchUsuarioOutboundPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatchClienteAdapter implements PatchUsuarioOutboundPort {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper mapper;

    public PatchClienteAdapter(ClienteRepository clienteRepository, ClienteMapper mapper) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    @Override
    public ClienteDTO atualizarClientes(ClienteDTO clienteDTO) {
        Optional<Cliente> existingClienteOpt = clienteRepository.findByCpf(clienteDTO.getCpf());

        if (!existingClienteOpt.isPresent()) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        Cliente existingCliente = existingClienteOpt.get();
        Cliente updatedCliente = new Cliente(
                existingCliente.getId(),
                existingCliente.getCpf(),
                clienteDTO.getNome().isEmpty() ? existingCliente.getNome() : clienteDTO.getNome(),
                clienteDTO.getEmail().isEmpty() ? existingCliente.getEmail() : clienteDTO.getEmail()
                );

        updatedCliente = clienteRepository.saveAndFlush(updatedCliente);
        return mapper.toDTO(updatedCliente);
    }
}
