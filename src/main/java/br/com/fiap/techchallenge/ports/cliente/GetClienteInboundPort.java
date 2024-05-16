package br.com.fiap.techchallenge.ports.cliente;

import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;

import java.util.List;

public interface GetClienteInboundPort {
    List<ClienteDTO> listarClientes(Integer page, Integer size, String email, String cpf);
}
