package br.com.fiap.techchallenge.infra.gateways.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.ProdutoEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


@CacheConfig(cacheNames = "produto")
public class ProdutoRepository implements IProdutoRepository {

    private final ProdutoEntityRepository produtoEntityRepository;
    private final CategoriaEntityRepository categoriaEntityRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoRepository(ProdutoEntityRepository produtoEntityRepository, CategoriaEntityRepository categoriaEntityRepository, ProdutoMapper produtoMapper) {
        this.produtoEntityRepository = produtoEntityRepository;
        this.categoriaEntityRepository = categoriaEntityRepository;
        this.produtoMapper = produtoMapper;
    }

    @Override
    public List<Produto> listarProdutos(String nome, String descricao, BigDecimal preco) {
        List<ProdutoEntity> listaProdutoEntity = new ArrayList<>();
        Predicate<ProdutoEntity> byNomeOrDescricaoOrPreco = produto -> {
            Boolean hasSameNome = nome == null || produto.getNome().equals(nome);
            Boolean hasSameDescricao = descricao == null || produto.getDescricao().equals(descricao);
            Boolean hasSamePreco = preco == null || produto.getPreco().equals(preco);

            return hasSameNome && hasSameDescricao && hasSamePreco;
        };

        if (nome != null || descricao != null || preco != null) {
            produtoEntityRepository.findByNomeOrDescricaoOrPreco(nome, descricao, preco).ifPresent(produtoList -> {
                List<ProdutoEntity> filteredProdutoEntity = produtoList.stream().filter(byNomeOrDescricaoOrPreco).toList();
                listaProdutoEntity.addAll(filteredProdutoEntity);
            });
        } else {
            listaProdutoEntity.addAll(findAll());
        }

        return produtoMapper.fromListEntityToListDomain(listaProdutoEntity);
    }

    @Cacheable(cacheNames = "produto")
    private List<ProdutoEntity> findAll() {
        return produtoEntityRepository.findAll();
    }

    @Override
    public List<Produto> listarProdutosPorCategoria(CategoriaEnum categoriaEnum) {
        Optional<List<ProdutoEntity>> optionalProdutos = produtoEntityRepository.findAllByCategoriaEntityId(categoriaEnum.getIdCategoria());
        if (optionalProdutos.isEmpty()) {
            throw new ProdutoException(ErrosEnum.PRODUTO_CATEGORIA_NAO_ENCONTRADO);
        }
        return produtoMapper.fromListEntityToListDomain(optionalProdutos.get());
    }

    @Override
    public Produto criarProduto(Produto produto) {

        Optional<CategoriaEntity> categoriaEntityOptional = categoriaEntityRepository.findByNome(produto.getCategoria().getNome());
        if (categoriaEntityOptional.isPresent()) {

            Optional<ProdutoEntity> produtoExists = produtoEntityRepository.findByNome(produto.getNome());
            if(produtoExists.isPresent()) {
                throw new ProdutoException(ErrosEnum.PRODUTO_JA_EXISTE);
            }

            ProdutoEntity produtoEntity = produtoMapper.fromDomainToEntity(produto);
            produtoEntity.setCategoriaEntity(categoriaEntityOptional.get());
            produtoEntity = produtoEntityRepository.saveAndFlush(produtoEntity);

            return produtoMapper.fromEntityToDomain(produtoEntity);
        }

        return null;
    }

    @Override
    public void deletarProduto(Long id) {
        Optional<ProdutoEntity> produtoEntityRepositoryById = produtoEntityRepository.findById(id);
        if (produtoEntityRepositoryById.isEmpty()) {
            throw new ProdutoException(ErrosEnum.PRODUTO_CODIGO_IDENTIFICADOR_INVALIDO);
        }
        executeDelete(id);
    }

    @CacheEvict(cacheNames = "produto", key = "#id", allEntries = true)
    public void executeDelete(Long id) {
        produtoEntityRepository.deleteById(id);
    }

    @Override
    @CachePut(cacheNames = "produto", key = "#id")
    public Produto atualizarDadosProduto(Long id, JsonPatch patch) {
        Optional<ProdutoEntity> produtoOptional = produtoEntityRepository.findById(id);
        if (produtoOptional.isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode patched = patch.apply(objectMapper.convertValue(produtoOptional.get(), JsonNode.class));
                ProdutoEntity produtoEntity = objectMapper.treeToValue(patched, ProdutoEntity.class);

                Optional<CategoriaEntity> categoriaOptional = categoriaEntityRepository.findById(id);
                categoriaOptional.ifPresent(produtoEntity::setCategoriaEntity);
                return produtoMapper.fromEntityToDomain(produtoEntityRepository.saveAndFlush(produtoEntity));
            } catch (JsonPatchException jsonPatchException) {
                throw new ProdutoException(ErrosEnum.PRODUTO_FALHA_DURANTE_ATUALIZACAO);
            } catch (Exception e) {
                throw new ProdutoException(ErrosEnum.PRODUTO_FALHA_GENERICA);
            }
        } else {
            throw new ProdutoException(ErrosEnum.PRODUTO_NAO_ENCONTRADO);
        }
    }

    @Override
    @CachePut(cacheNames = "produto", key = "#id")
    public Produto atualizarProduto(Long id, Produto produto) {
        ProdutoEntity novoProdutoEntity = produtoMapper.fromDomainToEntity(produto);

        try {
            Optional<ProdutoEntity> produtoOptional = produtoEntityRepository.findById(id);
            if (produtoOptional.isPresent()) {
                Optional<CategoriaEntity> categoriaEntityOptional = categoriaEntityRepository.findByNome(produto.getCategoria().getNome());
                categoriaEntityOptional.ifPresent(novoProdutoEntity::setCategoriaEntity);

                ProdutoEntity antigoProdutoEntity = produtoOptional.get();
                preencherComDadosNovos(antigoProdutoEntity, novoProdutoEntity);

                ProdutoEntity save = produtoEntityRepository.saveAndFlush(antigoProdutoEntity);
                return produtoMapper.fromEntityToDomain(save);
            } else {
                throw new ProdutoException(ErrosEnum.PRODUTO_CODIGO_IDENTIFICADOR_INVALIDO);
            }

        }catch (Exception e) {
            throw new ProdutoException(ErrosEnum.PRODUTO_CODIGO_IDENTIFICADOR_INVALIDO);
        }
    }

    @Override
    @Cacheable(value = "produto", key = "#id")
    public Produto findById(Long id) {
        Optional<ProdutoEntity> result = produtoEntityRepository.findById(id);
        if(result.isEmpty()) {
            throw new ProdutoException(ErrosEnum.PRODUTO_NAO_ENCONTRADO);
        }
        return produtoMapper.fromEntityToDomain(result.get());
    }

    private void preencherComDadosNovos(ProdutoEntity antigoProdutoEntity, ProdutoEntity novoProdutoEntity) {
        antigoProdutoEntity.setNome(novoProdutoEntity.getNome());
        antigoProdutoEntity.setDescricao(novoProdutoEntity.getDescricao());
        antigoProdutoEntity.setCategoriaEntity(novoProdutoEntity.getCategoriaEntity());
        antigoProdutoEntity.setPreco(novoProdutoEntity.getPreco());
        antigoProdutoEntity.setImagem(novoProdutoEntity.getImagem());
    }
}
