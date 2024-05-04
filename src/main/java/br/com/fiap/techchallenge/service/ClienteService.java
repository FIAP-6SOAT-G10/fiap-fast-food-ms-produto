package br.com.fiap.techchallenge.service;

import br.com.fiap.techchallenge.model.dto.ClienteDTO;
import br.com.fiap.techchallenge.model.entity.Cliente;
import br.com.fiap.techchallenge.model.mapper.ClienteMapper;
import br.com.fiap.techchallenge.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public List<ClienteDTO> listarClientes(Integer page, Integer size, String email, String cpf) {
        List<Cliente> clientes = new ArrayList<>();
        if (email != null && cpf != null) {
            Optional<Cliente> cliente = repository.findByEmailAndCpf(email, cpf);
            cliente.ifPresent(clientes::add);
        } else if (email != null) {
            Optional<Cliente> cliente = repository.findByEmail(email);
            cliente.ifPresent(clientes::add);
        } else if (cpf != null) {
            Optional<Cliente> cliente = repository.findByCpf(cpf);
            cliente.ifPresent(clientes::add);
        } else {
            PageRequest pageable = PageRequest.of(page, size);
            clientes.addAll(repository.findAll(pageable).toList());
        }
        return ClienteMapper.INSTANCE.fromListEntityToListDTO(clientes);
    }

}
