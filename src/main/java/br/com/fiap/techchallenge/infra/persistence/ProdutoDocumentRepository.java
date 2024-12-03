package br.com.fiap.techchallenge.infra.persistence;

import br.com.fiap.techchallenge.infra.persistence.document.ProdutoDocument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoDocumentRepository extends CrudRepository<ProdutoDocument, Long> {


}