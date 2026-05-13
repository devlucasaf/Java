# 🚀 API REST - CRUD de Produtos

API REST completa com Spring Boot para gerenciamento de produtos.

## Conceitos praticados
- **Spring Boot** (auto-configuration, starters)
- **API REST** (GET, POST, PUT, DELETE)
- **Spring Data JPA** (Repository Pattern)
- **Banco H2** em memória (zero configuração)
- **Bean Validation** (`@Valid`, `@NotBlank`, `@DecimalMin`)
- **DTOs** com Java Records (`ProdutoRequest`, `ProdutoResponse`)
- **Exception Handling** global com `@RestControllerAdvice`
- **Paginação e ordenação** automáticas
- **Soft Delete** (inativar ao invés de remover)
- **Arquitetura em camadas**: Controller → Service → Repository → Model

## Estrutura do projeto
```
api-produtos/
├── pom.xml
└── src/main/java/org/application/apiprodutos/
    ├── ApiProdutosApplication.java      ← Ponto de entrada
    ├── controller/
    │   └── ProdutoController.java       ← Endpoints REST
    ├── service/
    │   └── ProdutoService.java          ← Lógica de negócio
    ├── repository/
    │   └── ProdutoRepository.java       ← Acesso ao banco (JPA)
    ├── model/
    │   ├── Produto.java                 ← Entidade JPA
    │   └── Categoria.java               ← Enum de categorias
    ├── dto/
    │   ├── ProdutoRequest.java          ← DTO de entrada (Record)
    │   └── ProdutoResponse.java         ← DTO de saída (Record)
    └── exception/
        ├── ProdutoNaoEncontradoException.java
        └── GlobalExceptionHandler.java  ← Tratamento global de erros
```

## Como executar
```bash
cd api-produtos
mvn spring-boot:run
```

A API estará disponível em: **http://localhost:8080**

## Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/produtos` | Lista todos (paginado) |
| GET | `/api/produtos/{id}` | Busca por ID |
| GET | `/api/produtos/buscar?nome=mac` | Busca por nome |
| GET | `/api/produtos/categoria/ELETRONICOS` | Filtra por categoria |
| GET | `/api/produtos/preco?min=100&max=1000` | Filtra por faixa de preço |
| POST | `/api/produtos` | Cria produto |
| PUT | `/api/produtos/{id}` | Atualiza produto |
| DELETE | `/api/produtos/{id}` | Soft delete (inativa) |
| DELETE | `/api/produtos/{id}/permanente` | Deleta permanentemente |

## Exemplos com cURL

### Listar todos os produtos
```bash
curl http://localhost:8080/api/produtos
```

### Listar com paginação (página 0, 5 itens, ordenado por preço)
```bash
curl "http://localhost:8080/api/produtos?page=0&size=5&sort=preco,asc"
```

### Buscar por ID
```bash
curl http://localhost:8080/api/produtos/1
```

### Buscar por nome
```bash
curl "http://localhost:8080/api/produtos/buscar?nome=iphone"
```

### Filtrar por categoria
```bash
curl http://localhost:8080/api/produtos/categoria/ELETRONICOS
```

### Filtrar por faixa de preço
```bash
curl "http://localhost:8080/api/produtos/preco?min=100&max=1000"
```

### Criar produto
```bash
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Mouse Gamer",
    "descricao": "Mouse 16000 DPI RGB",
    "preco": 249.90,
    "quantidadeEstoque": 50,
    "categoria": "ELETRONICOS"
  }'
```

### Atualizar produto
```bash
curl -X PUT http://localhost:8080/api/produtos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "MacBook Pro 16 M4",
    "descricao": "Notebook Apple com chip M4 Pro",
    "preco": 21999.99,
    "quantidadeEstoque": 10,
    "categoria": "ELETRONICOS"
  }'
```

### Deletar produto (soft delete)
```bash
curl -X DELETE http://localhost:8080/api/produtos/1
```

## Console H2 (banco de dados)
Acesse: **http://localhost:8080/h2-console**
- JDBC URL: `jdbc:h2:mem:produtosdb`
- User: `sa`
- Password: *(vazio)*

## Categorias disponíveis
`ELETRONICOS`, `ROUPAS`, `MOVEIS`, `LIVROS`, `ACESSORIOS`, `ALIMENTOS`, `ESPORTES`, `OUTROS`

