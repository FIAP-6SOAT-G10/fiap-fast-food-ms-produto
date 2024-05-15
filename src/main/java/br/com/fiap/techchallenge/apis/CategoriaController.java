package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.GetCategoriaAdapter;
import br.com.fiap.techchallenge.domain.usecases.GetCategoriaUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.CategoriaDTO;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final GetCategoriaAdapter adapter;

    @Operation(summary = "Listar Categorias", description = "Esta operação deve ser utilizada para consultar a lista de categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "204", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            })
    })
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<CategoriaDTO>> listarTodasCategorias() {
        GetCategoriaUseCase getCategoriaUseCase = new GetCategoriaUseCase(adapter);
        List<CategoriaDTO> categorias = getCategoriaUseCase.listarCategorias();
        if (categorias.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }

}