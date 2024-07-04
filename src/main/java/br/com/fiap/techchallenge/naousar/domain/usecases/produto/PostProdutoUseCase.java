//package br.com.fiap.techchallenge.naousar.domain.usecases.produto;
//
//import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
//import br.com.fiap.techchallenge.domain.ErrosEnum;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.ProdutoDTO;
//import br.com.fiap.techchallenge.naousar.infra.exception.BaseException;
//import br.com.fiap.techchallenge.naousar.infra.exception.CategoriaException;
//import br.com.fiap.techchallenge.naousar.infra.exception.ProdutoException;
//import br.com.fiap.techchallenge.naousar.ports.produto.PostProdutoInboundPort;
//import br.com.fiap.techchallenge.naousar.ports.produto.PostProdutoOutboundPort;
//
//public class PostProdutoUseCase implements PostProdutoInboundPort {
//
//    private final PostProdutoOutboundPort port;
//
//    public PostProdutoUseCase(PostProdutoOutboundPort port) {
//        this.port = port;
//    }
//
//    @Override
//    public ProdutoEntity criarProduto(ProdutoDTO produtoDTO) throws BaseException {
//        validar(produtoDTO);
//        return this.port.criarProduto(produtoDTO);
//    }
//
//    private void validar(ProdutoDTO produtoDTO) throws BaseException {
//        if (produtoDTO.getNome() == null || produtoDTO.getNome().isEmpty()) {
//            throw new ProdutoException(ErrosEnum.PRODUTO_NOME_OBRIGATORIO, "O nome do produto é obrigatório");
//        }
//
//        if (produtoDTO.getDescricao() == null || produtoDTO.getDescricao().isEmpty()) {
//            throw new ProdutoException(ErrosEnum.PRODUTO_DESCRICAO_OBRIGATORIO, "A descrição do produto é obrigatória");
//        }
//
//        if (produtoDTO.getCategoria() == null) {
//            throw new CategoriaException(ErrosEnum.CATEGORIA_INVALIDA, "A categoria do produto é obrigatória");
//        }
//
//        if (produtoDTO.getPreco() == null) {
//            throw new ProdutoException(ErrosEnum.PRODUTO_PRECO_OBRIGATORIO, "O preço do produto é obrigatório");
//        }
//
//        if (produtoDTO.getImagem() == null) {
//            throw new ProdutoException(ErrosEnum.PRODUTO_IMAGEM_OBRIGATORIO, "A imagem do produto é obrigatória");
//        }
//    }
//}
