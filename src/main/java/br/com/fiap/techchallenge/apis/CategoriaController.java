package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.categoria.GetCategoriaAdapter;
import br.com.fiap.techchallenge.domain.model.mapper.categoria.CategoriaMapper;
import br.com.fiap.techchallenge.domain.usecases.categoria.GetCategoriaUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.CategoriaDTO;
import br.com.fiap.techchallenge.infra.repositories.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorias", description = "Conjunto de operações que podem ser realizadas no contexto de categorias de produtos.")
public class CategoriaController {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;

    @Operation(summary = "Listar Categorias", description = "Esta operação deve ser utilizada para consultar a lista de categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "204", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            })
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<CategoriaDTO>> listarTodasCategorias() {
        GetCategoriaUseCase getCategoriaUseCase = new GetCategoriaUseCase(new GetCategoriaAdapter(repository, mapper));
        List<CategoriaDTO> categorias = getCategoriaUseCase.listarCategorias();
        if (categorias.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }

}