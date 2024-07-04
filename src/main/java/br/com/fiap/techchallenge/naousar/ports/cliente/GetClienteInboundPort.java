package br.com.fiap.techchallenge.naousar.ports.cliente;

import br.com.fiap.techchallenge.naousar.domain.valueobjects.ClienteDTO;

import java.util.List;

public interface GetClienteInboundPort {
    List<ClienteDTO> listarClientes(Integer page, Integer size, String email, String cpf);
}
