package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.produtos.GetProdutoAdapter;
import br.com.fiap.techchallenge.adapters.produtos.PostProdutoAdapter;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.ErrorsResponse;
import br.com.fiap.techchallenge.domain.model.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.domain.usecases.produtos.GetProdutosUseCase;
import br.com.fiap.techchallenge.domain.usecases.produtos.PostProdutoUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.produtos.GetProdutoOutboundPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final PostProdutoAdapter postProdutoAdapter;
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
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProdutoDTO>> listarProdutos(@RequestParam Integer page,
                                                           @RequestParam Integer size,
                                                           @RequestParam(required = false) String nome,
                                                           @RequestParam(required = false) String descricao,
                                                           @RequestParam(required = false) BigDecimal preco
                                                           ) {
        GetProdutoOutboundPort getProdutoOutboundPort = new GetProdutoAdapter(produtoRepository, produtoMapper);
        GetProdutosUseCase getProdutosUseCase = new GetProdutosUseCase(getProdutoOutboundPort);
        List<ProdutoDTO> produtos = getProdutosUseCase.listarProdutos(page, size, nome, descricao, preco);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }




}