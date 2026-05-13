package org.application.apiprodutos.controller;

import jakarta.validation.Valid;
import org.application.apiprodutos.dto.ProdutoRequest;
import org.application.apiprodutos.dto.ProdutoResponse;
import org.application.apiprodutos.model.Categoria;
import org.application.apiprodutos.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> listarTodos(
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.listarTodos(pageable));
    }

    /**
     * GET /api/produtos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    /**
     * GET /api/produtos/buscar?nome=mac
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<ProdutoResponse>> buscarPorNome(
            @RequestParam String nome,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.buscarPorNome(nome, pageable));
    }

    /**
     * GET /api/produtos/categoria/ELETRONICOS
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoResponse>> buscarPorCategoria(
            @PathVariable Categoria categoria) {
        return ResponseEntity.ok(service.buscarPorCategoria(categoria));
    }

    /**
     * GET /api/produtos/preco?min=100&max=1000
     */
    @GetMapping("/preco")
    public ResponseEntity<List<ProdutoResponse>> buscarPorFaixaPreco(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(service.buscarPorFaixaPreco(min, max));
    }

    /**
     * POST /api/produtos
     * Body JSON: { "nome": "...", "descricao": "...", "preco": 99.90, "quantidadeEstoque": 10, "categoria": "ELETRONICOS" }
     */
    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest request) {
        ProdutoResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * PUT /api/produtos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    /**
     * DELETE /api/produtos/{id} (soft delete - marca como inativo)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/produtos/{id}/permanente (remove do banco)
     */
    @DeleteMapping("/{id}/permanente")
    public ResponseEntity<Void> deletarPermanente(@PathVariable Long id) {
        service.deletarPermanente(id);
        return ResponseEntity.noContent().build();
    }
}

