package com.fabrica.service;

import com.fabrica.model.Tijolo;
import com.fabrica.dto.RelatorioDTO;
import com.fabrica.dto.TijoloDTO;
import com.fabrica.model.StatusTijolo;

import java.util.List;
import java.util.Optional;

public interface TijoloService {

    List<Tijolo> listarTodos();

    Optional<Tijolo> buscarPorId(Long id);

    Tijolo criarTijolo(TijoloDTO dto);

    Tijolo criarTijoloAleatorio();

    Tijolo alterarStatus(Long id, StatusTijolo novoStatus);

    void deletarSeDefeituoso(Long id);

    List<Tijolo> filtrar(StatusTijolo status, String cor, Boolean defeituoso);

    RelatorioDTO gerarRelatorio();
}