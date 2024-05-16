package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.cliente.PutClienteAdapter;
import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.model.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClientePutControllerTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private PutClienteAdapter putClienteAdapter;
    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        putClienteAdapter = new PutClienteAdapter(clienteRepository, clienteMapper);
    }

    @Test
    void shouldReturnOkWhenUpdatingExistingClient() {
        ClienteDTO clienteDTO = new ClienteDTO("23456789012", "Test Client 2", "test2@email.com");
        Cliente existingCliente = Cliente.builder().cpf("23456789012").email("Test Client 2").nome("test2@email.com").id(12L).build();
        when(clienteRepository.findByCpf(any())).thenReturn(Optional.of(existingCliente));
        when(putClienteAdapter.atualizarClientes(clienteDTO)).thenReturn(clienteDTO);
        ResponseEntity<ClienteDTO> response = clienteController.atualizarCliente(clienteDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteDTO, response.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingClient() {
        ClienteDTO clienteDTO = new ClienteDTO("12345678901", "Teste", "email@email.com");
        when(clienteRepository.findByCpf(any())).thenReturn(null);

        assertThrows(ClienteException.class, () -> clienteController.atualizarCliente(clienteDTO));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingClientWithEmptyCpf() {
        ClienteDTO clienteDTO = new ClienteDTO("", "Teste", "email@email.com");

        assertThrows(ClienteException.class, () -> clienteController.atualizarCliente(clienteDTO));
    }
}