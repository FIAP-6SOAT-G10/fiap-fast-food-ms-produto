package br.com.fiap.techchallenge.application.gateways;

import br.com.fiap.techchallenge.domain.entities.cliente.Cliente;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;
import java.util.Optional;

public interface IClienteRepository {

    Cliente atualizarDadosCliente(Long id, JsonPatch patch);

    Cliente atualizarCliente(Long id, Cliente cliente);

    Cliente salvarCliente(Cliente cliente);

    List<Cliente> listarClientes(Integer page, Integer size, String email, String cpf);

    Optional<Cliente> findByCpf(String cpf);
}
