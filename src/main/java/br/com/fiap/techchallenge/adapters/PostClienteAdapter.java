package br.com.fiap.techchallenge.adapters;

import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.model.mapper.ClienteMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import br.com.fiap.techchallenge.ports.PostClienteOutboundPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static br.com.fiap.techchallenge.domain.model.enums.ErrosEnum.CLIENTE_JA_CADASTRADO;

@Slf4j
public class PostClienteAdapter implements PostClienteOutboundPort {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper mapper;

    public PostClienteAdapter(ClienteRepository clienteRepository, ClienteMapper mapper) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    @Override
    public ClienteDTO salvarCliente(ClienteDTO clienteDTO) {
        log.info("salvarCliente");
        return mapper.toDTO(clienteRepository.save(mapper.toEntity(clienteDTO)));
    }

    @Override
    public void validarCpfCadastrado(String cpf) {
        log.info("validarCpfCadastrado");
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);
        if(clienteOptional.isPresent()) {
            throw new ClienteException(CLIENTE_JA_CADASTRADO);
        }
    }
}
