package com.exercicio.produtos.controller;

import com.exercicio.produtos.model.Produto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    // Armazenamento em memória (lista estática)
    private static final List<Produto> produtos = new ArrayList<>();
    private static int proximoId = 1;

    // GET /produtos -> lista completa
    @GetMapping
    public List<Produto> listar() {
        return produtos;
    }

    // GET /produtos/{id} -> um produto específico
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Integer id) {
        return buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /produtos -> adiciona um novo produto
    @PostMapping
    public ResponseEntity<Produto> adicionar(@RequestBody Produto produto) {
        produto.setId(proximoId++);
        produtos.add(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    // PATCH /produtos/{id} -> atualiza os campos enviados
    @PatchMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Integer id, @RequestBody Produto dados) {
        Optional<Produto> existente = buscar(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Produto produto = existente.get();
        if (dados.getNome() != null) {
            produto.setNome(dados.getNome());
        }
        if (dados.getPreco() != null) {
            produto.setPreco(dados.getPreco());
        }
        return ResponseEntity.ok(produto);
    }

    // DELETE /produtos/{id} -> remove pelo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        boolean removido = produtos.removeIf(p -> p.getId().equals(id));
        return removido
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    private Optional<Produto> buscar(Integer id) {
        return produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }
}
