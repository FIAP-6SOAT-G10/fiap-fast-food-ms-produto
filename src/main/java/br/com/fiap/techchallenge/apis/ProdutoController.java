package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.PatchProdutoAdapter;
import br.com.fiap.techchallenge.adapters.PostProdutoAdapter;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.ErrorsResponse;
import br.com.fiap.techchallenge.domain.model.mapper.ProdutoMapper;
import br.com.fiap.techchallenge.domain.usecases.PatchProdutoUseCase;
import br.com.fiap.techchallenge.domain.usecases.PostProdutoUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.repositories.CategoriaRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.PatchProdutoOutboundPort;
import br.com.fiap.techchallenge.ports.PostProdutoOutboundPort;
import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

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

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProdutoMapper mapper;

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

        PostProdutoOutboundPort postProdutoAdapter = new PostProdutoAdapter(produtoRepository, mapper);
        PostProdutoUseCase postProdutoUseCase = new PostProdutoUseCase(postProdutoAdapter);
        Produto produto = postProdutoUseCase.criarProduto(produtoDTO);

        return ResponseEntity.created(UriComponentsBuilder.fromPath("/produtos/{id}").buildAndExpand(produto.getId()).toUri()).build();
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
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Produto> atualizarDadosProduto(@PathVariable("id") String id, @RequestBody JsonPatch patch) {
        log.info("Atualizando dados de um produto.");
        PatchProdutoOutboundPort patchProdutoAdapter = new PatchProdutoAdapter(produtoRepository, categoriaRepository);
        PatchProdutoUseCase patchProdutoUseCase = new PatchProdutoUseCase(patchProdutoAdapter);
        Produto produto = patchProdutoUseCase.atualizarDadosProduto(id, patch);

        return ResponseEntity.ok(produto);
    }

}