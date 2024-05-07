package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.PostProdutoAdapter;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.ErrorsResponse;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.domain.usecases.PostProdutoUseCase;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final PostProdutoAdapter adapter;

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
        PostProdutoUseCase postProdutoUseCase = new PostProdutoUseCase(adapter);
        Produto produto = postProdutoUseCase.criarProduto(produtoDTO);

        return ResponseEntity.created(UriComponentsBuilder.fromPath("/produtos/{id}").buildAndExpand(produto.getId()).toUri()).build();
    }

}