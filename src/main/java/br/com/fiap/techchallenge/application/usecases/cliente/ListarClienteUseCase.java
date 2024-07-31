package br.com.fiap.techchallenge.application.usecases.cliente;

import br.com.fiap.techchallenge.application.gateways.IClienteRepository;
import br.com.fiap.techchallenge.domain.entities.cliente.Cliente;

import java.util.List;

public class ListarClienteUseCase {

    private final IClienteRepository clienteRepository;

    public ListarClienteUseCase(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarClientes(String email, String cpf) {
        return this.clienteRepository.listarClientes(email, cpf);
    }
}
