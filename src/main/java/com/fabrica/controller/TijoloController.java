package com.fabrica.controller;

import com.fabrica.dto.RelatorioDTO;
import com.fabrica.dto.TijoloDTO;
import com.fabrica.model.StatusTijolo;
import com.fabrica.model.Tijolo;
import com.fabrica.service.TijoloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tijolos")
@RequiredArgsConstructor
public class TijoloController {

    private final TijoloService service;

    @GetMapping
    public List<Tijolo> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tijolo> buscarPorId(@PathVariable("id") Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tijolo> criarTijolo(@Valid @RequestBody TijoloDTO dto) {
        Tijolo novo = service.criarTijolo(dto);
        return ResponseEntity.status(201).body(novo);
    }

    @PostMapping("/aleatorio")
    public Tijolo criarTijoloAleatorio() {
        return service.criarTijoloAleatorio();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Tijolo> alterarStatus(@PathVariable("id") Long id,
                                                @RequestParam("status") StatusTijolo status) {
        Tijolo atualizado = service.alterarStatus(id, status);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        service.deletarSeDefeituoso(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtro")
    public List<Tijolo> filtrar(
            @RequestParam(name = "status", required = false) StatusTijolo status,
            @RequestParam(name = "cor", required = false) String cor,
            @RequestParam(name = "defeituoso", required = false) Boolean defeituoso
    ) {
        return service.filtrar(status, cor, defeituoso);
    }

    @GetMapping("/relatorio")
    public RelatorioDTO gerarRelatorio() {
        return service.gerarRelatorio();
    }
}