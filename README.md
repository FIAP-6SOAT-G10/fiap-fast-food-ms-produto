### TODO - Remover mocks de inserts na tabela
# PosTech - Arquitetura de Software - FIAP
<img height="350" src="src/main/resources/fiap.png" title="Fiap Logo" width="1000"/>

# Documentação - Tech Challenge - Grupo 10 6SOAT
Repositório para o desafio do Tech Challenge da Pós-gradução em Software Architecture pela FIAP.

## Introdução
Uma lanchonete de bairro que está expandido sua operação devido seu grande sucesso. Porém, com a expansão e sem um sistema de controle de pedidos, o atendimento aos clientes pode ser caótico e confuso.
Para solucionar o problema, a lanchonete irá investir em um sistema de autoatendimento de fast food, que é composto por uma série de dispositivos e interfaces que permitem aos clientes selecionar e fazer pedidos sem precisar interagir com um atendente.

## Membros do Grupo nota 10
- [Anderson Oliveira](https://github.com/anderson-solucoes)
- [Gabriel Fonte](https://github.com/sourceGabriel)
- [Gabriel Marcelino](https://github.com/GabsMarcelino)
- [Rafael Moura](https://github.com/magneon)
- [Thiago Getnerski](https://github.com/Getnerski)

## Documentação do Projeto com Event Storming
Fizemos o mapeamento do fluxo de forma evolutiva, pensando a partir dos eventos passados, incrementando com os pontos de atenção para na sequência adicionar os comandos, modelos de leitura, políticas e sistemas externos, conforme o que foi aprendido em aula.

[Event Storming](https://miro.com/app/board/uXjVKVpAtxk=/?moveToWidget=3458764589554562116&cot=14)

### Requisitos
1. [Docker](https://www.docker.com/)
2. [DBeaver](https://dbeaver.io/download/)
3. [Maven](https://maven.apache.org/)
4. [Postman](https://www.postman.com/downloads/) (opcional, pois há Swagger em /api/api-docs ou /api/swagger-ui.html)

Antes de iniciar, certifique-se de que sua máquina atenda aos seguintes requisitos:<br/>

**JDK 11 instalado:**<br/>
Certifique-se de ter o JDK 11 instalado em sua máquina.<br/>
Para verificar a versão do JDK instalada, execute o seguinte comando no terminal: 
```bash
java -version
```

**Maven >= 3 instalado:**<br/>
Verifique se você tem o Maven instalado em sua máquina. Para verificar a versão do Maven instalada, execute o seguinte comando no terminal: 
```bash
mvn -version
``` 

**Docker desktop instalado:**<br/>
Verifique se você tem o Docker instalado em sua máquina. Para verificar a versão do Docker instalada, execute o seguinte comando no terminal: 
```bash
docker -v
``` 
Se o Docker não estiver instalado, faça o download e siga as instruções de instalação do site oficial do Docker ou de outra fonte confiável.<br/><br/>

**Clonando o projeto:**<br/>
Clone o repositório do projeto em seu ambiente local.<br/>
```bash
git clone https://github.com/FIAP-6SOAT-G10/tech-challenge.git
```

## Execução do projeto localmente

### Passo 1: Iniciar o Docker

Na raiz do projeto, execute o seguinte comando para criar o container com o banco de dados Postgres:

```bash
docker-compose up -d
```

### Passo 2: Executar as Migrations
Feita a criação do container, basta executar o comando abaixo para carregar as tabelas do banco de dados com estrutura e dados iniciais:

```bash
mvn flyway:migrate -Plocal
```

### Passo 3: Executar o projeto
Após a execução das migrations, você pode iniciar o projeto.

```bash
mvn spring-boot:run
```

### Documentação
A documentação da API pode ser acessada através do Swagger em: [http://localhost:8080/api/swagger-ui.html](`http://localhost:8080/api/swagger-ui.html`)

### Testes
Para executar os testes unitários, basta executar o comando abaixo:

`mvn test`

### Observações
- O arquivo `application.yml` contém as configurações de conexão com o banco de dados. Caso seja necessário alterar a porta do banco de dados, basta alterar a propriedade `spring.datasource.url` no arquivo `application.yml`.

## Rotas da API
A aplicação fornece as seguintes rotas:

### Rotas de Categorias
- `GET /categorias`: Retorna a lista de categorias de produtos cadastradas no sistema. Atualmente, temos as categorias LANCHE, ACOMPANHAMENTO, BEBIDA e SOBREMESA.

### Rotas de Clientes
- `POST /clientes`: Cria um novo cliente. Retorna 201 se for bem-sucedido, 400 se houver uma solicitação ruim e 500 para erros internos do servidor.
- `GET /clientes`: Retorna uma lista de todos os clientes. Retorna 200 se for bem-sucedido, 204 se nenhum conteúdo for encontrado e 500 para erros internos do servidor.
- `PATCH /clientes`: Atualiza os dados do cliente. Retorna 200 se for bem-sucedido, 400 se houver uma solicitação ruim, 404 se não for encontrado e 500 para erros internos do servidor.
- `PUT /clientes`: Atualiza um cliente. Retorna 204 se for bem-sucedido, 400 se houver uma solicitação ruim, 404 se não for encontrado e 500 para erros internos do servidor.

### Rotas de Produtos
- `POST /produtos`: Cria um novo produto. Retorna 201 se for bem-sucedido, 400 se houver uma solicitação ruim e 500 para erros internos do servidor.
- `GET /produtos`: Retorna uma lista de todos os produtos. Retorna 200 se for bem-sucedido, 204 se nenhum conteúdo for encontrado e 500 para erros internos do servidor.
- `PATCH /produtos/{id}`: Atualiza os dados do produto. Retorna 200 se for bem-sucedido, 400 se houver uma solicitação ruim, 404 se não for encontrado e 500 para erros internos do servidor.
- `PUT /produtos/{id}`: Atualiza um produto. Retorna 204 se for bem-sucedido, 400 se houver uma solicitação ruim, 404 se não for encontrado e 500 para erros internos do servidor.
- `DELETE /produtos/{id}`: Exclui um produto. Retorna 200 se for bem-sucedido, 204 se nenhum conteúdo for encontrado, 400 se houver uma solicitação ruim, 404 se não for encontrado e 500 para erros internos do servidor.
- `GET /produtos/categoria/{categoria}`: Retorna uma lista de produtos por categoria. Retorna 200 se for bem-sucedido, 204 se nenhum conteúdo for encontrado, 400 se houver uma solicitação ruim, 404 se não for encontrado e 500 para erros internos do servidor.

### Rotas de Pedidos
- `GET /pedidos/{id}`: Retorna um pedido específico. Retorna 200 se for bem-sucedido, 404 se não for encontrado e 500 para erros internos do servidor.
- `GET /pedidos`: Retorna uma lista de todos os pedidos. Retorna 200 se for bem-sucedido, 204 se nenhum conteúdo for encontrado e 500 para erros internos do servidor.
- `POST /pedidos/{id}/checkout`: Realiza o checkout de um pedido. Retorna 201 se for bem-sucedido, 400 se houver uma solicitação ruim e 500 para erros internos do servidor.
- `PATCH /pedidos/{id}`: Atualiza o status de um pedido. Retorna 200 se for bem-sucedido, 400 se houver algum problema na solicitação e 500 para erros internos do servidor.
- `GET /pedidos/status/{status}`: Retorna todos os pedidos em um determinado status. Os status disponíveis são: Recebido (recebido), Em Preparação (preparacao), Pronto (pronto) e Finalizado (finalizado). Retorna 200 se for bem-sucedido, 400 se houver algum problema na solicitação e 500 para erros internos do servidor.

## Códigos de Erro

### Erros genéricos:
- `001`: Parâmetro obrigatório não foi enviado.
### Erros de categoria:
- `100`: Categoria inválida.
### Erros de produtos:
- `200`: O nome do produto é obrigatório.
- `201`: A descrição do produto é obrigatória.
- `202`: O preço do produto é obrigatório.
- `203`: A imagem do produto é obrigatória.
- `204`: Identificador de produto inválido.
- `205`: O identificador fornecido não está relacionado a nenhum produto existente.
- `206`: Erro durante a atualização do produto no banco de dados.
- `207`: Erro genérico ao atualizar o produto.
- `208`: A categoria do produto é obrigatória.
- `209`: Não existem produtos registrados para a categoria informada.
### Erros de clientes:
- `300`: CPF inválido.
- `301`: O cliente com o CPF informado já existe.
- `302`: O email do cliente é obrigatório.
- `303`: O CPF do cliente é obrigatório.
- `304`: O campo CPF é obrigatório ao atualizar um cliente.
- `305`: O nome do cliente é obrigatório.
### Erros de pedidos:
- `400`: O pedido informado não foi localizado.
- `401`: Erro durante a atualização do status do pedido no banco de dados.
- `402`: O identificador do pedido é inválido.
- `403`: O status à ser atualizado é obrigatório.
- `404`: Não é possível atualizar o pedido para o status informado.
- `405`: Pedidos no status 'Recebido' só podem avançar para o status 'Em preparação'.
- `406`: Pedidos no status 'Em preparação' só podem avançar para o status 'Pronto'.
- `407`: Pedidos no status 'Pronto' só podem avançar para o status 'Finalizado'.
- `408`: Pedidos no status 'Finalizado' não podem ser alterados.
- `499`: Erro genérico ao atualizar o status do pedido.