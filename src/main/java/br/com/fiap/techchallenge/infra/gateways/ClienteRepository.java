package br.com.fiap.techchallenge.infra.gateways;

import br.com.fiap.techchallenge.application.gateways.IClienteRepository;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.cliente.Cliente;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.infra.persistence.ClienteEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.entities.ClienteEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ClienteRepository implements IClienteRepository {

    private final ClienteEntityRepository clienteEntityRepository;
    private final ClienteMapper clienteMapper;

    public ClienteRepository(ClienteEntityRepository clienteEntityRepository, ClienteMapper clienteMapper) {
        this.clienteEntityRepository = clienteEntityRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public Cliente atualizarDadosCliente(Long id, JsonPatch patch) {
        Optional<ClienteEntity> clienteOptional = clienteEntityRepository.findById(id);
        if (clienteOptional.isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode patched = patch.apply(objectMapper.convertValue(clienteOptional.get(), JsonNode.class));
                ClienteEntity clienteEntity = objectMapper.treeToValue(patched, ClienteEntity.class);

                return clienteMapper.fromEntityToDomain(clienteEntityRepository.saveAndFlush(clienteEntity));
            } catch (JsonPatchException jsonPatchException) {
                throw new ClienteException(ErrosEnum.CLIENTE_FALHA_DURANTE_ATUALIZACAO);
            } catch (Exception e) {
                throw new ClienteException(ErrosEnum.CLIENTE_FALHA_GENERICA);
            }
        } else {
            throw new ClienteException(ErrosEnum.CLIENTE_NAO_ENCONTRADO);
        }
    }

    @Override
    public Cliente atualizarCliente(Long id, Cliente cliente) {
        ClienteEntity novoClienteEntity = clienteMapper.fromDomainToEntity(cliente);

        Optional<ClienteEntity> clienteOptional = clienteEntityRepository.findById(id);

        if (clienteOptional.isPresent()) {
            ClienteEntity antigoClienteEntity = clienteOptional.get();
            preencherComDadosNovos(antigoClienteEntity, novoClienteEntity);
            return clienteMapper.fromEntityToDomain(clienteEntityRepository.saveAndFlush(antigoClienteEntity));
        } else {
            throw new ProdutoException(ErrosEnum.CLIENTE_CODIGO_IDENTIFICADOR_INVALIDO);
        }
    }

    @Override
    public Cliente salvarCliente(Cliente cliente) {
        return clienteMapper.fromEntityToDomain(clienteEntityRepository.saveAndFlush(clienteMapper.fromDomainToEntity(cliente)));
    }

    @Override
    public List<Cliente> listarClientes(String email, String cpf) {
        List<ClienteEntity> listaClienteEntity = new ArrayList<>();
        Predicate<ClienteEntity> predicate = cliente -> {
            Boolean hasSameEmail = email == null || cliente.getEmail().equals(email);
            Boolean hasSameCpf = cpf == null || cliente.getCpf().equals(cpf);
            return hasSameEmail && hasSameCpf;
        };

        if (email != null || cpf != null) {
            clienteEntityRepository.findByEmailOrCpf(email, cpf).ifPresent(clienteList -> {
                List<ClienteEntity> filteredClientesEntity = clienteList.stream().filter(predicate).toList();
                listaClienteEntity.addAll(filteredClientesEntity);
            });
        } else {
            listaClienteEntity.addAll(clienteEntityRepository.findAll());
        }

        return clienteMapper.fromListEntityToListDomain(listaClienteEntity);
    }

    @Override
    public Optional<Cliente> findByCpf(String cpf) {
        return clienteEntityRepository.findByCpf(cpf)
                .map(clienteMapper::fromEntityToDomain);
    }

    private void preencherComDadosNovos(ClienteEntity antigoClienteEntity, ClienteEntity novoClienteEntity) {
        antigoClienteEntity.setCpf(novoClienteEntity.getCpf());
        antigoClienteEntity.setNome(novoClienteEntity.getNome());
        antigoClienteEntity.setEmail(novoClienteEntity.getEmail());
    }
}
