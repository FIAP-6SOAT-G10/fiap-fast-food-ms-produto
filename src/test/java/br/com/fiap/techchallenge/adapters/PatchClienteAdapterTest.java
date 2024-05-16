package br.com.fiap.techchallenge.adapters;

import br.com.fiap.techchallenge.adapters.cliente.PatchClienteAdapter;
import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.model.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PatchClienteAdapterTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper mapper;

    private PatchClienteAdapter patchClienteAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patchClienteAdapter = new PatchClienteAdapter(clienteRepository, mapper);
    }

    @Test
    void shouldUpdateClienteWhenValidInputProvided() {
        ClienteDTO clienteDTO = new ClienteDTO();
        Cliente existingCliente = new Cliente();
        Cliente updatedCliente = new Cliente();

        when(clienteRepository.findByCpf(clienteDTO.getCpf())).thenReturn(Optional.of(existingCliente));
        when(clienteRepository.saveAndFlush(any(Cliente.class))).thenReturn(updatedCliente);
        when(mapper.toDTO(updatedCliente)).thenReturn(clienteDTO);

        ClienteDTO result = patchClienteAdapter.atualizarClientes(clienteDTO);

        assertEquals(clienteDTO, result);
    }
    @Test
    void shouldThrowExceptionWhenClienteNotFound() {
        ClienteDTO clienteDTO = new ClienteDTO();

        when(clienteRepository.findByCpf(clienteDTO.getCpf())).thenReturn(Optional.empty());

        assertThrows(ClienteException.class, () -> patchClienteAdapter.atualizarClientes(clienteDTO));
    }
}