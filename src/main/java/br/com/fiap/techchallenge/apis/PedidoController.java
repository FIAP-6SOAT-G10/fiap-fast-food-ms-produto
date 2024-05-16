package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Pedidos", description = "Conjunto de operações que podem ser realizadas no contexto de pedidos.")
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    private final PedidoMapper pedidoMapper;

}