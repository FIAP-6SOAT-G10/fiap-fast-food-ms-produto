package br.com.fiap.techchallenge.service;
import br.com.fiap.techchallenge.model.dto.ClienteDTO;
import br.com.fiap.techchallenge.model.entity.Cliente;
import br.com.fiap.techchallenge.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTests {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void ItShouldListarTodosClientes10PrimeirosClientes() {
        int page = 0;
        int size = 10;
        when(repository.findAll(PageRequest.of(page, size))).thenReturn(GetMockPageCliente());

        List<ClienteDTO> result = clienteService.listarClientes(page, size, null, null);

        assertEquals(4, result.size());
        assertEquals(result.get(0).getCpf(), "12312312312");
        assertEquals(result.get(0).getEmail(), "email@email.com");
    }

    @Test
    void itShouldRetornarClienteComEmailCpfEspecificado() {
        String email = "email@email.com";
        String cpf = "12312312312";
        when(repository.findByEmailOrCpf(email, cpf)).thenReturn(getMockClientes());

        List<ClienteDTO> result = clienteService.listarClientes(null, null, email, cpf);

        assertEquals(1, result.size());
    }

    @Test
    void itShouldRetornarClienteComEmailEspecificado() {
        String email = "email@email.com";
        when(repository.findByEmailOrCpf(email, null)).thenReturn(getMockClientes());

        List<ClienteDTO> result = clienteService.listarClientes(null, null, email, null);

        assertEquals(1, result.size());
    }

    @Test
    void itShouldRetornarClienteComCpfEspecificado() {
        String cpf = "99998889090";
        when(repository.findByEmailOrCpf(null, cpf)).thenReturn(getMockClientes());

        List<ClienteDTO> result = clienteService.listarClientes(null, null, null, cpf);

        assertEquals(1, result.size());
    }

    @Test
    void itShouldReturnEmpty() {
        String cpf = "1111111111111111111";
        when(repository.findByEmailOrCpf(null, cpf)).thenReturn(getMockClientes());

        List<ClienteDTO> result = clienteService.listarClientes(null, null, null, cpf);

        assertEquals(0, result.size());
    }

    private Page<Cliente> GetMockPageCliente() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1L, "12312312312", "Nome", "email@email.com"));
        clientes.add(new Cliente(1L, "99998889090", "Garzdi", "isabellafabiananascimento@mectron.com.br"));
        clientes.add(new Cliente(1L, "16708812029", "Halsiuil", "camila.sandra.freitas@plenamenterh.com.br"));
        clientes.add(new Cliente(1L, "87747536062", "Kharsudbu", "carloseduardoferreira@marmorariauchoa.com"));
        return new PageImpl<>(clientes);
    }

    private Optional<List<Cliente>> getMockClientes() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1L, "12312312312", "Nome", "email@email.com"));
        clientes.add(new Cliente(1L, "99998889090", "Garzdi", "isabellafabiananascimento@mectron.com.br"));
        clientes.add(new Cliente(1L, "16708812029", "Halsiuil", "camila.sandra.freitas@plenamenterh.com.br"));
        clientes.add(new Cliente(1L, "87747536062", "Kharsudbu", "carloseduardoferreira@marmorariauchoa.com"));
        return Optional.of(clientes);
    }

}
