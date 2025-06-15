package com.fabrica.dto;

import jakarta.validation.constraints.*;

public class TijoloDTO {

    @NotBlank(message = "A cor do tijolo é obrigatória.")
    private String cor;

    @NotBlank(message = "O número de furos é obrigatório.")
    @Pattern(regexp = "[1-8]", message = "O número de furos deve ser de 1 a 8.")
    private String furos;

    // Getters e Setters
    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getFuros() {
        return furos;
    }

    public void setFuros(String furos) {
        this.furos = furos;
    }
}