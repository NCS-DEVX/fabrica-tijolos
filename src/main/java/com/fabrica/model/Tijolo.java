package com.fabrica.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tijolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cor; // branco ou preto

    private String furos; // entre "1" e "8"

    @Enumerated(EnumType.STRING)
    private StatusTijolo status;

    private Boolean defeituoso;
}