package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.produto.*;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.ErrorsResponse;
import br.com.fiap.techchallenge.domain.model.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.domain.usecases.produto.*;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.repositories.CategoriaRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.produto.*;
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

import javax.validation.Valid;
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

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

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
    public ResponseEntity<Void> cadastrarProduto(@RequestBody @Valid ProdutoDTO produtoDTO) throws BaseException {
        log.info("Criando novo produto.");

        PostProdutoOutboundPort postProdutoAdapter = new PostProdutoAdapter(produtoRepository, produtoMapper);
        PostProdutoUseCase postProdutoUseCase = new PostProdutoUseCase(postProdutoAdapter);
        Produto produto = postProdutoUseCase.criarProduto(produtoDTO);

        return ResponseEntity.created(UriComponentsBuilder.fromPath("/produtos/{id}").buildAndExpand(produto.getId()).toUri()).build();
    }

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
    public ResponseEntity<List<ProdutoDTO>> listarProdutos(@RequestParam Integer page,
                                                           @RequestParam Integer size,
                                                           @RequestParam(required = false) String nome,
                                                           @RequestParam(required = false) String descricao,
                                                           @RequestParam(required = false) BigDecimal preco
                                                           ) {
        GetProdutoOutboundPort getProdutoOutboundPort = new GetProdutoAdapter(produtoRepository, produtoMapper);
        GetProdutoUseCase getProdutosUseCase = new GetProdutoUseCase(getProdutoOutboundPort);
        List<ProdutoDTO> produtos = getProdutosUseCase.listarProdutos(page, size, nome, descricao, preco);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @Operation(summary = "Atualizar Dados do Produto", description = "Esta operação deve ser utilizada para atualizar dados de um produto individualmente", requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = {
                    @Content(examples = {
                            @ExampleObject(name = "Valid Request", value = ProdutoController.VALID_REQUEST),
                            @ExampleObject(name = "Invalid Request", value = ProdutoController.INVALID_REQUEST)
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
    public ResponseEntity<Produto> atualizarDadosProduto(@PathVariable("id") String id, @RequestBody JsonPatch patch) {
        log.info("Atualizando dados de um produto.");
        PatchProdutoOutboundPort patchProdutoAdapter = new PatchProdutoAdapter(produtoRepository, categoriaRepository);
        PatchProdutoUseCase patchProdutoUseCase = new PatchProdutoUseCase(patchProdutoAdapter);
        Produto produto = patchProdutoUseCase.atualizarDadosProduto(id, patch);

        return ResponseEntity.ok(produto);
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
        PutProdutoOutboundPort putProdutoAdapter = new PutProdutoAdapter(produtoRepository, produtoMapper);
        PutProdutoInboundPort putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);
        Produto produto = putProdutoUseCase.atualizarProduto(id, produtoDTO);
        if (produto == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<Produto> deletarProduto(@PathVariable("id") String id) {
        log.info("Deletando o produto.");
        DeleteProdutoOutboundPort deleteProdutoAdapter = new DeleteProdutoAdapter(produtoRepository);
        DeleteProdutoInboundPort deleteProdutoUseCase = new DeleteProdutoUseCase(deleteProdutoAdapter);
        Produto produto = deleteProdutoUseCase.deletarProduto(id);
        if (produto == null) {
            ResponseEntity.notFound().build();
        }
        log.info("Produto deletado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(produto);
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
    public ResponseEntity<List<Produto>> buscarProdutosPorCategoria(@PathVariable("categoria") String categoria) {
        log.info("Listando produtos por categoria");
        GetProdutoOutboundPort getProdutoAdapter = new GetProdutoAdapter(produtoRepository, produtoMapper);
        GetProdutoInboundPort getProdutoUseCase = new GetProdutoUseCase(getProdutoAdapter);
        List<Produto> produtos = getProdutoUseCase.listarProdutosPorCategoria(categoria);
        if (produtos == null || produtos.isEmpty()) {
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }

}