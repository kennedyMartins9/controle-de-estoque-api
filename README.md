<h1 align="center">📦 Controle de Estoque API</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL">
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker">
  <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="Swagger">
</p>

<p align="center">
  <strong>Status do Projeto:</strong> ✔️ Concluído
</p>

> API RESTful completa de controle de estoque desenvolvida em **Java Spring Boot**, projetada para portfólio com foco em boas práticas da engenharia de software (Clean Code e SOLID), conteinerização via **Docker** e qualidade garantida com **Testes Unitários**.

<br>

## 🚀 Funcionalidades e Requisitos Atendidos

- **CRUD Completo de Produto**: Cadastro, Listagem, Busca, Atualização e Deleção (`/products`).
- **Regras de Negócio Avançadas (Service Layer)**:
  - Impede estoque negativo com exceção clara.
  - Movimentação de estoque isolada via `PATCH` (entrada e saída por quantidade).
- **OperationType como Enum**: `ENTRY` e `EXIT` fortemente tipados — nenhum valor inválido passa pelo sistema.
- **Proteção da Rota PUT**: A edição de nome/preço **nunca** altera o estoque acidentalmente — quantidade só é modificada via `PATCH`.
- **Histórico de Movimentações (`StockMovement`)**: Toda entrada e saída é registrada em tabela separada com produto, tipo, quantidade e data.
- **Tratamento Global de Erros**: `GlobalExceptionHandler` mapeando respostas limpas para erros de validação e regra de negócio.
- **Validação de Dados Estrita**: Anotações `@NotBlank`, `@Min`, `@NotNull` e `@Valid` via `spring-boot-starter-validation`.
- **Isolamento de Domínio (DTOs)**: `ProductDTO` para leitura, `ProductUpdateDTO` para edição cadastral (sem `quantity`) e `StockUpdateDTO` para movimentações.
- **Testes Unitários**: JUnit 5 e Mockito cobrindo entrada, saída, insuficiência de saldo e integridade do `updateProduct`.
- **Documentação de API**: Integração nativa com SpringDoc OpenAPI (Swagger).

---

## 🏗️ Arquitetura e Tecnologias

- **Linguagem:** Java 17
- **Framework:** Spring Boot 3.x (Web, Data JPA, Validation)
- **Banco de Dados (Produção/Docker):** MySQL 8.0
- **Banco de Dados (Testes):** H2 Database (In-Memory)
- **Containerização:** Docker & Docker Compose
- **Testes:** JUnit 5, Mockito
- **Gerenciador de Dependências:** Maven

---

## 📁 Estrutura do Projeto

```
src/main/java/com/kennedy/controle_estoque
├── controller
├── service
├── repository
├── model
├── dto
└── exception
```

---

## 🐳 Como Executar o Projeto (Via Docker - Recomendado)

A forma ideal de subir o sistema. O Docker já se encarregará de subir a API Spring Boot junto do serviço MySQL configurado.

1. **Pré-requisito:** Certifique-se de ter o **Docker** e **Docker Compose** instalados e em execução.
2. Clone o repositório e abra o terminal na pasta raiz do projeto.
3. Suba os containers com:
   ```bash
   docker-compose up -d --build
   ```
4. A aplicação estará ativa na porta `8080`.
5. **Acesse a Documentação (Swagger):** http://localhost:8080/swagger-ui/index.html
6. **Banco de Dados:** O MySQL estará exposto na porta `3307` caso você queira conectar por um painel (usuário: `root` / senha: `root`).

---

## 💻 Como Executar Localmente (Desenvolvimento)

1. Tenha o **MySQL** rodando e crie um banco chamado `estoque`.
2. Ajuste as credenciais no `application.properties` se necessário (padrão: `root/root`).
3. Para rodar a bateria de **Testes Automatizados** (usa H2 em memória, não afeta o MySQL):
   ```bash
   ./mvnw clean test
   ```
4. Para inicializar a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 🔗 Endpoints da API (Base: `/products`)

| HTTP Method | Endpoint | Descrição | Status Sucesso |
|-------------|----------|-----------|----------------|
| `GET`    | `/products`                  | Lista todos os produtos. | `200 OK` |
| `GET`    | `/products/{id}`             | Retorna um produto pelo ID. | `200 OK` |
| `POST`   | `/products`                  | Cria um novo produto. | `201 Created` |
| `PUT`    | `/products/{id}`             | Atualiza **nome e preço** (não altera estoque). | `200 OK` |
| `PATCH`  | `/products/{id}`             | Movimenta o estoque (`ENTRY` ou `EXIT`). | `200 OK` |
| `DELETE` | `/products/{id}`             | Exclui o produto. | `200 OK` |
| `GET`    | `/products/{id}/movements`   | Lista o histórico de movimentações do produto. | `200 OK` |

**Exemplo de Criação (`POST /products`):**
```json
{
  "name": "Notebook Dell",
  "quantity": 50,
  "price": 4500.00
}
```

**Exemplo de Atualização Cadastral (`PUT /products/1`):**
```json
{
  "name": "Notebook Dell XPS",
  "price": 5000.00
}
```

**Exemplo de Movimentação de Estoque (`PATCH /products/1`):**
```json
{
  "quantity": 10,
  "operationType": "EXIT"
}
```
> `operationType` aceita apenas os valores `ENTRY` ou `EXIT` (Enum).

**Exemplo de Resposta do Histórico (`GET /products/1/movements`):**
```json
[
  {
    "id": 2,
    "operationType": "EXIT",
    "quantity": 10,
    "movementDate": "2026-03-18T17:05:00"
  },
  {
    "id": 1,
    "operationType": "ENTRY",
    "quantity": 50,
    "movementDate": "2026-03-18T16:50:00"
  }
]
```

---

*Projeto desenvolvido como demonstração de conhecimento avançado em backend/Java.*
