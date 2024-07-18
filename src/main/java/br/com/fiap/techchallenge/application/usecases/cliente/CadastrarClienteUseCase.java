package br.com.fiap.techchallenge.application.usecases.cliente;

import br.com.fiap.techchallenge.application.gateways.IClienteRepository;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.cliente.Cliente;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.ClienteException;

import java.util.Optional;

public class CadastrarClienteUseCase {

    private final IClienteRepository clienteRepository;

    public CadastrarClienteUseCase(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente salvarCliente(Cliente cliente) {
        validarDados(cliente);
        return this.clienteRepository.salvarCliente(cliente);
    }

    private void validarDados(Cliente cliente) throws BaseException {
        if (valueIsNullOrEmpty(cliente.getNome())) {
            throw new ClienteException(ErrosEnum.CLIENTE_NOME_OBRIGATORIO);
        }

        if (valueIsNullOrEmpty(cliente.getEmail())) {
            throw new ClienteException(ErrosEnum.CLIENTE_EMAIL_OBRIGATORIO);
        }

        if (valueIsNullOrEmpty(cliente.getCpf())) {
            throw new ClienteException(ErrosEnum.CLIENTE_CPF_INVALIDO);
        }

        validarCpfCadastrado(cliente.getCpf());
    }

    public static boolean valueIsNullOrEmpty(String value) {
        return (value == null || value.isEmpty());
    }

    public void validarCpfCadastrado(String cpf) {
        Optional<Cliente> cliente = clienteRepository.findByCpf(cpf);
        if (cliente.isPresent()) {
            throw new ClienteException(ErrosEnum.CLIENTE_JA_CADASTRADO);
        }
    }

}
