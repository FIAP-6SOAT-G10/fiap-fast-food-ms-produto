package br.com.fiap.techchallenge.infra.gateways;

import br.com.fiap.techchallenge.infra.persistence.ProdutoDocumentRepository;
import br.com.fiap.techchallenge.infra.persistence.document.ProdutoDocument;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "produto")
public class ProdutoRedisRepository {

    private final ProdutoDocumentRepository repository;

    public ProdutoRedisRepository(ProdutoDocumentRepository repository) {
        this.repository = repository;
    }

    @Cacheable(cacheNames = "produto")
    public List<ProdutoDocument> findAll(){
        return (List<ProdutoDocument>) repository.findAll();
    }

    @CacheEvict(cacheNames = "produto", allEntries = true)
    public ProdutoDocument save(ProdutoDocument document){
        return repository.save(document);
    }

    @Cacheable(cacheNames = "produto", key = "#id", unless = "#result == null")
    public Optional<ProdutoDocument> findById(Long id){

       return repository.findById(id);
    }

    private void waitSomeTime(){
        try{
            Thread.sleep(300);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
