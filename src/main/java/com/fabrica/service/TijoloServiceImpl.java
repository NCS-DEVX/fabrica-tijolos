package com.fabrica.service;

import com.fabrica.dto.RelatorioDTO;
import com.fabrica.dto.TijoloDTO;
import com.fabrica.model.StatusTijolo;
import com.fabrica.model.Tijolo;
import com.fabrica.repository.TijoloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TijoloServiceImpl implements TijoloService {

    private final TijoloRepository repository;
    private final Random random = new Random();

    @Override
    public List<Tijolo> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Tijolo> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Tijolo criarTijolo(TijoloDTO dto) {
        Tijolo tijolo = Tijolo.builder()
                .cor(dto.getCor().toLowerCase()) // padroniza entrada
                .furos(dto.getFuros())
                .status(StatusTijolo.EM_INSPECAO)
                .defeituoso(null)
                .build();

        return repository.save(tijolo);
    }

    @Override
    public Tijolo criarTijoloAleatorio() {
        Tijolo tijolo = Tijolo.builder()
                .cor(random.nextBoolean() ? "branco" : "preto")
                .furos(String.valueOf(random.nextInt(8) + 1))
                .status(StatusTijolo.EM_INSPECAO)
                .defeituoso(null)
                .build();
        return repository.save(tijolo);
    }

    @Override
    public Tijolo alterarStatus(Long id, StatusTijolo novoStatus) {
        Tijolo tijolo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tijolo não encontrado"));

        if (tijolo.getStatus() != StatusTijolo.EM_INSPECAO) {
            throw new RuntimeException("Status não pode ser alterado");
        }

        tijolo.setStatus(novoStatus);

        if (novoStatus == StatusTijolo.APROVADO) {
            boolean defeito = random.nextInt(3) == 0;
            tijolo.setDefeituoso(defeito);
        } else {
            tijolo.setDefeituoso(false);
        }

        return repository.save(tijolo);
    }

    @Override
    public void deletarSeDefeituoso(Long id) {
        Tijolo tijolo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tijolo não encontrado"));

        if (Boolean.TRUE.equals(tijolo.getDefeituoso())) {
            repository.delete(tijolo);
        } else {
            throw new RuntimeException("Tijolo não pode ser deletado");
        }
    }

    @Override
    public List<Tijolo> filtrar(StatusTijolo status, String cor, Boolean defeituoso) {
        return repository.filtrar(status, cor, defeituoso);
    }

    @Override
    public RelatorioDTO gerarRelatorio() {
        List<Tijolo> todos = repository.findAll();

        long total = todos.size();
        long aprovados = todos.stream().filter(t -> t.getStatus() == StatusTijolo.APROVADO).count();
        long reprovados = todos.stream().filter(t -> t.getStatus() == StatusTijolo.REPROVADO).count();
        long emInspecao = todos.stream().filter(t -> t.getStatus() == StatusTijolo.EM_INSPECAO).count();
        long defeituosos = todos.stream().filter(t -> Boolean.TRUE.equals(t.getDefeituoso())).count();
        long aprovadosComDefeito = todos.stream().filter(t ->
                t.getStatus() == StatusTijolo.APROVADO && Boolean.TRUE.equals(t.getDefeituoso())).count();

        long brancosFurosPares = todos.stream()
                .filter(t -> "branco".equalsIgnoreCase(t.getCor()) && isPar(t.getFuros()))
                .count();

        long brancosFurosImpares = todos.stream()
                .filter(t -> "branco".equalsIgnoreCase(t.getCor()) && !isPar(t.getFuros()))
                .count();

        long pretosFurosPares = todos.stream()
                .filter(t -> "preto".equalsIgnoreCase(t.getCor()) && isPar(t.getFuros()))
                .count();

        long pretosFurosImpares = todos.stream()
                .filter(t -> "preto".equalsIgnoreCase(t.getCor()) && !isPar(t.getFuros()))
                .count();

        long totalBrancos = todos.stream().filter(t -> "branco".equalsIgnoreCase(t.getCor())).count();
        long totalPretos = todos.stream().filter(t -> "preto".equalsIgnoreCase(t.getCor())).count();

        return new RelatorioDTO(
                total,
                aprovados,
                reprovados,
                emInspecao,
                defeituosos,
                aprovadosComDefeito,
                brancosFurosPares,
                brancosFurosImpares,
                pretosFurosPares,
                pretosFurosImpares,
                totalBrancos,
                totalPretos
        );
    }

    private boolean isPar(String valor) {
        try {
            int numero = Integer.parseInt(valor);
            return numero % 2 == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}