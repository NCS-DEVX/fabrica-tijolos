package com.fabrica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioDTO {
    private long totalTijolos;
    private long totalAprovados;
    private long totalReprovados;
    private long totalEmInspecao;
    private long totalDefeituosos;
    private long totalAprovadosComDefeito;
    private long brancosFurosPares;
    private long brancosFurosImpares;
    private long pretosFurosPares;
    private long pretosFurosImpares;
    private long totalBrancos;
    private long totalPretos;
}