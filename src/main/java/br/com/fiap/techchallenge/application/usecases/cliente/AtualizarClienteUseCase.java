package br.com.fiap.techchallenge.application.usecases.cliente;

import br.com.fiap.techchallenge.application.gateways.IClienteRepository;
import br.com.fiap.techchallenge.domain.entities.cliente.Cliente;

public class AtualizarClienteUseCase {

    private final IClienteRepository clienteRepository;

    public AtualizarClienteUseCase(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente atualizarClientes(String id, Cliente cliente) {
        return this.clienteRepository.atualizarCliente(Long.valueOf(id), cliente);
    }
}
