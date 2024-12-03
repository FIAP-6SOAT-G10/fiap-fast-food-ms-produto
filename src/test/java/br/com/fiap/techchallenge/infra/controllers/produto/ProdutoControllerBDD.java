package br.com.fiap.techchallenge.infra.controllers.produto;

import io.cucumber.java.es.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class ProdutoControllerBDD {


    private static final String ENDPOINT_PAYMENT = "http://localhost:8080/api/produtos";

    private RequestSpecification request;
    private Response response;

    @Dado("que recebo os parametros de consulta de produto valido")
    public void que_recebo_os_dados_para_cadastra_um_novo_produto(){

        String nome = "Big Fiap";
        String descricao = "Dois hambúrgueres, alface americana, queijo cheddar, maionese, cebola, picles e pão com gergelim";
        BigDecimal preco = new BigDecimal("26.9");

        request = given()
                .pathParams(
                        "nome", nome,
                        "descricao", descricao,
                        "preco", preco
                );
    }


    @Quando("realizar a busca do produto")
    public void realizar_a_busca_do_produto(){
        response = request.when().get(ENDPOINT_PAYMENT);
    }

    @Quando("o produto nao existir")
    public void o_produto_nao_existir() {
        response.getBody();
    }

    @Entao("os detalhes do produto nao devem ser retornados")
    public void os_detalhes_do_produto_nao_devem_ser_retornados() {
        response.then();
    }

    @Entao("o codigo {int} deve ser apresentado")
    public void o_codigo_deve_ser_apresentado(Integer httpStatus) {
        response.then().statusCode(httpStatus);
    }

}
