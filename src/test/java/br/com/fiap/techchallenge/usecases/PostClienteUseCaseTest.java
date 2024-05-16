package br.com.fiap.techchallenge.usecases;

import br.com.fiap.techchallenge.adapters.PostClienteAdapter;
import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.model.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.domain.usecases.PostClienteUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostClienteUseCaseTest {

    @InjectMocks
    PostClienteAdapter adapter;

    @Mock
    ClienteRepository repository;

    @Mock
    ClienteMapper mapper;

    private ClienteDTO criarClienteRetorno() {
        return ClienteDTO
                .builder()
                .nome("John Doo")
                .email("email@email")
                .cpf("00000000000")
                .build();
    }

    private Optional<Cliente> criarClienteOptional() {
        return Optional.of(Cliente
                .builder()
                .nome("John Doo")
                .email("email@email")
                .cpf("00000000000")
                .build());
    }

    @Test
    void shouldCadastrarClienteComSucesso() throws BaseException {
        when(repository.findByCpf("00000000001")).thenReturn(criarClienteOptional());
        when(adapter.salvarCliente(any())).thenReturn(criarClienteRetorno());
        ClienteDTO clienteRequet = ClienteDTO
                .builder()
                .nome("John Doo")
                .email("email@email")
                .cpf("00000000000")
                .build();

        PostClienteUseCase postProdutoUseCase = new PostClienteUseCase(adapter);
        ClienteDTO produto = postProdutoUseCase.salvarCliente(clienteRequet);
        assertEquals("John Doo", produto.getNome());
        assertEquals("email@email", produto.getEmail());
        assertEquals("00000000000", produto.getCpf());
    }

    @Test
    void mustLancarClienteExceptionAoCadastrarClienteComCPFQueJaExiste() throws BaseException {
        when(repository.findByCpf("00000000000")).thenReturn(criarClienteOptional());
        when(adapter.salvarCliente(any())).thenReturn(criarClienteRetorno());
        ClienteDTO clienteRequet = ClienteDTO
                .builder()
                .nome("John Doo")
                .email("email@email")
                .cpf("00000000000")
                .build();

        PostClienteUseCase postProdutoUseCase = new PostClienteUseCase(adapter);
        assertThrows(ClienteException.class, () -> postProdutoUseCase.salvarCliente(clienteRequet));
    }

    @Test
    void mustLancarClienteExceptionAoCadastrarClienteComCPFVazio() throws BaseException {
        when(repository.findByCpf("00000000000")).thenReturn(criarClienteOptional());
        when(adapter.salvarCliente(any())).thenReturn(criarClienteRetorno());
        ClienteDTO clienteRequet = ClienteDTO
                .builder()
                .nome("John Doo")
                .email("email@email")
                .build();

        PostClienteUseCase postProdutoUseCase = new PostClienteUseCase(adapter);
        assertThrows(ClienteException.class, () -> postProdutoUseCase.salvarCliente(clienteRequet));
    }

    @Test
    void mustLancarClienteExceptionAoCadastrarClienteComNomeVazio() throws BaseException {
        when(repository.findByCpf("00000000000")).thenReturn(criarClienteOptional());
        when(adapter.salvarCliente(any())).thenReturn(criarClienteRetorno());
        ClienteDTO clienteRequet = ClienteDTO
                .builder()
                .email("email@email")
                .cpf("00000000000")
                .build();

        PostClienteUseCase postProdutoUseCase = new PostClienteUseCase(adapter);
        assertThrows(ClienteException.class, () -> postProdutoUseCase.salvarCliente(clienteRequet));
    }

    @Test
    void mustLancarClienteExceptionAoCadastrarClienteComEmailVazio() throws BaseException {
        when(repository.findByCpf("00000000000")).thenReturn(criarClienteOptional());
        when(adapter.salvarCliente(any())).thenReturn(criarClienteRetorno());
        ClienteDTO clienteRequet = ClienteDTO
                .builder()
                .nome("John Doo")
                .cpf("00000000000")
                .build();

        PostClienteUseCase postProdutoUseCase = new PostClienteUseCase(adapter);
        assertThrows(ClienteException.class, () -> postProdutoUseCase.salvarCliente(clienteRequet));
    }
}