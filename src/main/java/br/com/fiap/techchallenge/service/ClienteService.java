package br.com.fiap.techchallenge.service;

import br.com.fiap.techchallenge.model.dto.ClienteDTO;
import br.com.fiap.techchallenge.model.entity.Cliente;
import br.com.fiap.techchallenge.model.mapper.ClienteMapper;
import br.com.fiap.techchallenge.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public List<Cliente> listarClientes(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size);
        return repository.findAll(pageable).toList();
    }

}
