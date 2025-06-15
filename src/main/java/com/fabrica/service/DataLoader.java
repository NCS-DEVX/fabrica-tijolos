package com.fabrica.service;

import com.fabrica.model.StatusTijolo;
import com.fabrica.model.Tijolo;
import com.fabrica.repository.TijoloRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final TijoloRepository repository;
    private final Random random = new Random();

    @PostConstruct
    public void init() {
        if (repository.count() == 0) {
            IntStream.range(0, 100).forEach(i -> {
                Tijolo tijolo = Tijolo.builder()
                        .cor(random.nextBoolean() ? "branco" : "preto")
                        .furos(String.valueOf(random.nextInt(8) + 1)) // de 1 a 8
                        .status(StatusTijolo.EM_INSPECAO)
                        .defeituoso(null)
                        .build();
                repository.save(tijolo);
            });

            // Mensagem para o terminal
            System.out.println("100 tijolos gerados com sucesso!");
        }
    }
}