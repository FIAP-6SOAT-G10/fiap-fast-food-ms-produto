package br.com.fiap.techchallenge.infra.controllers.produto;

import br.com.fiap.techchallenge.application.usecases.produto.*;
import br.com.fiap.techchallenge.domain.ErrorsResponse;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.controllers.categoria.CategoriaDTO;
import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "Produtos", description = "Conjunto de operações que podem ser realizadas no contexto de produtos.")
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private static final String VALID_REQUEST = """
            [
                {
                    "op": "replace",
                    "path": "/nome",
                    "value": "NovoNome"
                }
            ]
    """;

    private static final String INVALID_REQUEST = """
            {
                "op": "replace",
                "path": "/nome",
                "value": "NovoNome"
            }
    """;

    private final ListarProdutoUseCase listarProdutoUseCase;
    private final CadastrarProdutoUseCase cadastrarProdutoUseCase;
    private final DeletarProdutoUseCase deletarProdutoUseCase;
    private final AtualizarProdutoParcialUseCase atualizarProdutoParcialUseCase;
    private final AtualizarProdutoUseCase atualizarProdutoUseCase;

    @Operation(summary = "Buscar Produtos", description = "Esta operação deve ser usada para buscar todos os itens cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found", content =
                    {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", description = "Not Found", content =
                    {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))})})
    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProdutoDTO>> listarProdutos(@RequestParam(required = false) String nome,
                                                           @RequestParam(required = false) String descricao,
                                                           @RequestParam(required = false) BigDecimal preco
    ) {
        List<Produto> produtos = listarProdutoUseCase.listarProdutos(nome, descricao, preco);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtos.stream().map(p -> new ProdutoDTO(p.getId(), p.getNome(), p.getDescricao(), new CategoriaDTO(p.getCategoria().getNome(), p.getCategoria().getDescricao()), p.getPreco(), p.getImagem())).toList());
    }

    @Operation(summary = "Listar Produtos por Categoria", description = "Esta operação deve ser utilizada para a consulta de produtos por categoria", parameters = {
            @Parameter(name = "categoria", examples = {
                    @ExampleObject(name = "LANCHE", value = "lanche", description = "Opções de lanches disponíveis para pedido"),
                    @ExampleObject(name = "ACOMPANHAMENTO", value = "acompanhamento", description = "Opções de acompanhamento disponíveis para pedido"),
                    @ExampleObject(name = "BEBIDA", value = "bebida", description = "Opções de bebida disponíveis para pedido"),
                    @ExampleObject(name = "SOBREMESA", value = "sobremesa", description = "Opções de sobremesa disponíveis para pedido"),
            })
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", description = "Ok", content =
                    {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))})})
    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping(path = "/categoria/{categoria}", produces = "application/json")
    public ResponseEntity<List<ProdutoDTO>> buscarProdutosPorCategoria(@PathVariable("categoria") String categoria) {
        log.info("Listando produtos por categoria");

        List<Produto> produtos = listarProdutoUseCase.listarProdutosPorCategoria(CategoriaEnum.fromName(categoria));
        if (produtos == null || produtos.isEmpty()) {
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos.stream().map(p -> new ProdutoDTO(p.getId(), p.getNome(), p.getDescricao(), new CategoriaDTO(p.getCategoria().getNome(), p.getCategoria().getDescricao()), p.getPreco(), p.getImagem())).toList());
    }

    @Operation(summary = "Cadastrar Produto", description = "Esta operação deve ser utilizada para cadastrar um novo produto no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))})})
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> cadastrarProduto(@RequestBody ProdutoDTO produtoDTO) {
        log.info("Criando novo produto.");
        Produto produto = cadastrarProdutoUseCase.criarProduto(new Produto(produtoDTO.nome(), produtoDTO.descricao(), new Categoria(produtoDTO.categoria()), produtoDTO.preco(), produtoDTO.imagem()));
        return ResponseEntity.created(UriComponentsBuilder.fromPath("/produtos/{id}").buildAndExpand(produto.getId()).toUri()).build();
    }

    @Operation(summary = "Deletar Produto", description = "Esta operação deve ser utilizada para deletar o produto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", description = "Ok", content =
                    {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))})})
    @CrossOrigin(origins = "*", maxAge = 3600)
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ProdutoEntity> deletarProduto(@PathVariable("id") String id) {
        log.info("Deletando o produto.");
        deletarProdutoUseCase.deletarProduto(id);

        log.info("Produto deletado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Atualizar Dados do Produto", description = "Esta operação deve ser utilizada para atualizar dados de um produto individualmente", requestBody =
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = {
            @Content(examples = {
                    @ExampleObject(name = "Valid Request", value = VALID_REQUEST),
                    @ExampleObject(name = "Invalid Request", value = INVALID_REQUEST)
            })
    }),
            externalDocs = @ExternalDocumentation(url = "https://jsonpatch.com/", description = "Utilize essa documentação para montar a PATCH request.")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))})})
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoDTO> atualizarDadosProduto(@PathVariable("id") String id, @RequestBody JsonPatch patch) {
        log.info("Atualizando dados de um produto.");
        Produto produto = atualizarProdutoParcialUseCase.atualizarDadosProduto(id, patch);

        return ResponseEntity.ok(new ProdutoDTO(produto.getId(), produto.getNome(), produto.getDescricao(), new CategoriaDTO(produto.getCategoria().getNome(), produto.getCategoria().getDescricao()), produto.getPreco(), produto.getImagem()));
    }

    @Operation(summary = "Atualizar Produto", description = "Esta operação deve ser utilizada para atualizar o produto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ok", content =
                    {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))})})
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<Void> atualizarProduto(@PathVariable("id") String id, @RequestBody ProdutoDTO produtoDTO) {
        log.info("Atualizando um produto.");
        Produto produto = atualizarProdutoUseCase.atualizarProduto(id, new Produto(produtoDTO.nome(), produtoDTO.descricao(), new Categoria(produtoDTO.categoria()), produtoDTO.preco(), produtoDTO.imagem()));
        if (produto == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}