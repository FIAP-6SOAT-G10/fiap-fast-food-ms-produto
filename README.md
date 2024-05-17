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
- [Thiago Getnerski coloca teu git aqui](https://github.com/GITHUBDOTHIAGOGETNERSKIAQUI)

### Requisitos
1. Docker
2. DBeaver
3. Maven
4. Postman (opcional, pois há Swagger em /api/api-docs ou /api/swagger-ui.html)

Antes de iniciar, certifique-se de que sua máquina atenda aos seguintes requisitos:<br/>

**JDK 11 instalado:**<br/>
Certifique-se de ter o JDK 11 instalado em sua máquina.<br/>
Para verificar a versão do JDK instalada, execute o seguinte comando no terminal: 
```bash
java -version.
```

**Maven >= 3 instalado:**<br/>
Verifique se você tem o Maven instalado em sua máquina. Para verificar a versão do Maven instalada, execute o seguinte comando no terminal: 
```bash
mvn -version.
``` 

**Docker desktop instalado:**<br/>
Verifique se você tem o Docker instalado em sua máquina. Para verificar a versão do Docker instalada, execute o seguinte comando no terminal: 
```bash
docker -v.
``` 
Se o Docker não estiver instalado, faça o download e siga as instruções de instalação do site oficial do Docker ou de outra fonte confiável.<br/><br/>

**Clonando o projeto:**<br/>
Clone o repositório do projeto em seu ambiente local.<br/>
```bash
git clone https://github.com/DesorganizationFiap/tech-challenge.git
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

## Rotas de Cliente

- `POST /clientes`: Cria um novo cliente.
- `GET /clientes`: Retorna todos os clientes.
- `PATCH /clientes`: Atualiza um cliente.
- `PUT /clientes`: Atualiza um cliente parcialmente.

## Rotas de Produto

- `POST /produtos`: Cria um novo produto.
- `PATCH /produtos/{id}`: Atualiza dados de um produto.
- `PUT /produtos/{id}`: Atualiza um produto.
- `DELETE /produtos/{id}`: Deleta um produto.

# Retornos de Erro
Este documento descreve os códigos de erro que a aplicação pode retornar, juntamente com suas respectivas mensagens.

## Erros Genéricos
- `001`: Parametro mandatorio não foi enviado
## Erros de Categoria
- `100`: Categoria inválida.
## Erros de Produto
- `200`: O campo nome é obrigatório.
- `201`: O campo descricao é obrigatório.
- `202`: O campo preco é obrigatório.
- `203`: O campo imagem é obrigatório.
- `204`: O identificador do produto é inválido.
- `205`: O identificador informado não está relacionado a nenhum produto existente.
- `206`: Erro durante a atualização do produto no banco de dados.
- `207`: Erro genérico ao atualizar o produto.
- `208`: O campo categoria é obrigatório.

## Erros de Cliente
- `300`: CPF inválido.
- `301`: O cliente com o CPF informado já existe.
- `302`: O email do cliente é obrigatório.
- `303`: O CPF do cliente é obrigatório.
- `304`: O campo cpf é obrigatório na atualização de um cliente.
- `305`: O nome do cliente é obrigatório.