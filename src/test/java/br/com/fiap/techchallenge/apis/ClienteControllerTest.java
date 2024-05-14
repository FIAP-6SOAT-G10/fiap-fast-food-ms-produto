package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.GetClienteAdapter;
import br.com.fiap.techchallenge.adapters.PatchClienteAdapter;
import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.model.mapper.ClienteMapper;
import br.com.fiap.techchallenge.domain.usecases.PatchClienteUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClienteControllerTest {

    @Mock
    private GetClienteAdapter getClienteAdapter;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private PatchClienteAdapter patchClienteAdapter;
    @Mock
    private PatchClienteUseCase patchClienteUseCase;
    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patchClienteAdapter = new PatchClienteAdapter(clienteRepository, clienteMapper);
    }

    @Test
    void shouldReturnNoContentWhenListarTodosClientesAndNoClientesExist() {
        when(getClienteAdapter.listarClientes(0, 10, null, null)).thenReturn(Collections.emptyList());
        ResponseEntity<List<ClienteDTO>> response = clienteController.listarTodosClientes(0, 10, null, null);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturnOkWhenListarTodosClientesAndClientesExist() {
        List<ClienteDTO> clientes = Collections.singletonList(new ClienteDTO());
        when(getClienteAdapter.listarClientes(0, 10, null, null)).thenReturn(clientes);
        ResponseEntity<List<ClienteDTO>> response = clienteController.listarTodosClientes(0, 10, null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturnOkWhenUpdatingExistingClient() {
        ClienteDTO clienteDTO = new ClienteDTO("42321973897", "Teste", "email@email.com");
        when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.of(new Cliente()));
        when(clienteMapper.toDTO(any())).thenReturn(clienteDTO);

        ResponseEntity<ClienteDTO> response = clienteController.atualizarClientes(clienteDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteDTO, response.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingClient() {
        ClienteDTO clienteDTO = new ClienteDTO("12345678901", "Teste", "email@email.com");
        when(clienteRepository.findByCpf(any())).thenReturn(null);

        assertThrows(ClienteException.class, () -> clienteController.atualizarClientes(clienteDTO));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingClientWithEmptyCpf() {
        ClienteDTO clienteDTO = new ClienteDTO("", "Teste", "email@email.com");

        assertThrows(ClienteException.class, () -> clienteController.atualizarClientes(clienteDTO));
    }


}