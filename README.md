# Exercício Módulo 43 — API REST de Produtos (Spring Boot)

API REST simples para gerenciar um cadastro de produtos, com os dados guardados **em memória** (lista estática), sem banco de dados.

## Tecnologias
- Java 17+
- Spring Boot 3.5 (spring-boot-starter-web)
- Maven (wrapper incluído: `mvnw` / `mvnw.cmd`)

## Estrutura
```
src/main/java/com/exercicio/produtos/
├── ProdutosApiApplication.java      # classe principal
├── model/Produto.java               # entidade: id (Integer), nome (String), preco (Double)
└── controller/ProdutoController.java # @RestController com os 5 endpoints
```

## Como rodar
```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```
A API sobe em `http://localhost:8080`.

## Endpoints

| Verbo  | Rota             | Anotações usadas                 | Descrição                              | Resposta            |
|--------|------------------|----------------------------------|----------------------------------------|---------------------|
| GET    | `/produtos`      | `@GetMapping`                    | Lista todos os produtos                | 200                 |
| GET    | `/produtos/{id}` | `@GetMapping` + `@PathVariable`  | Busca um produto pelo id               | 200 / 404           |
| POST   | `/produtos`      | `@PostMapping` + `@RequestBody`  | Cadastra um novo produto (id gerado)   | 201                 |
| PATCH  | `/produtos/{id}` | `@PatchMapping` + `@PathVariable` + `@RequestBody` | Atualiza só os campos enviados | 200 / 404 |
| DELETE | `/produtos/{id}` | `@DeleteMapping` + `@PathVariable` | Remove um produto pelo id            | 204 / 404           |

Todas as rotas partem de `@RequestMapping("/produtos")` no controller.

## Exemplos (curl)
```bash
# Criar
curl -X POST http://localhost:8080/produtos -H "Content-Type: application/json" -d "{\"nome\":\"Teclado\",\"preco\":150.0}"

# Listar
curl http://localhost:8080/produtos

# Buscar por id
curl http://localhost:8080/produtos/1

# Atualizar parcialmente
curl -X PATCH http://localhost:8080/produtos/1 -H "Content-Type: application/json" -d "{\"preco\":199.9}"

# Remover
curl -X DELETE http://localhost:8080/produtos/1
```

## Testes
```bash
mvnw.cmd test
```
O teste `ProdutoControllerTest` percorre o fluxo completo dos cinco endpoints com MockMvc.

> Os dados ficam só na memória: ao reiniciar a aplicação, a lista volta a ficar vazia.
