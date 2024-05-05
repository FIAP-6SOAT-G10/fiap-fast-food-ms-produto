# Tech Challenge
Repositório do projeto Tech Challenge do Grupo 10 da 6SOAT

## Execução do projeto localmente
### Requisitos
1. Docker
2. DBeaver
3. Maven
4. Postman (opcional, pois há Swagger em /api/api-docs ou /api/swagger-ui.html)

### Execução
Entrar na raiz do projeto através de um prompt de comando e executar o comando abaixo para criar o container com o banco de dados Postgres:

`docker-compose up -d`

Feito a criação do container, basta executar o comando abaixo para carregar as tabelas do banco de dados com estrutura e dados iniciais:

`mvn flyway:migrate -Dmigration=local`

Quando as migrations do Flyway forem executadas e finalizadas, basta dar um Run no projeto.