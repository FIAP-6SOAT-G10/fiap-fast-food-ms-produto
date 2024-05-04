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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public List<ClienteDTO> listarClientes(Integer page, Integer size, String email, String cpf) {

        List<Cliente> clientes = new ArrayList<>();
        Predicate<Cliente> predicate = cliente -> {
            Boolean hasSameEmail = email == null || cliente.getEmail().equals(email);
            Boolean hasSameCpf = cpf == null || cliente.getCpf().equals(cpf);
            return hasSameEmail && hasSameCpf;
        };

        if (email != null || cpf != null) {
            repository.findByEmailOrCpf(email, cpf).ifPresent(clienteList -> {
                List<Cliente> filteredClientes = clienteList.stream().filter(predicate).toList();
                clientes.addAll(filteredClientes);
            });
        } else {
            PageRequest pageable = PageRequest.of(page, size);
            clientes.addAll(repository.findAll(pageable).toList());
        }
        return ClienteMapper.INSTANCE.fromListEntityToListDTO(clientes);
    }

}
