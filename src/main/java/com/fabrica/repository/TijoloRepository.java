package com.fabrica.repository;

import com.fabrica.model.StatusTijolo;
import com.fabrica.model.Tijolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TijoloRepository extends JpaRepository<Tijolo, Long> {

    @Query("SELECT t FROM Tijolo t " +
           "WHERE (:status IS NULL OR t.status = :status) " +
           "AND (:cor IS NULL OR t.cor = :cor) " +
           "AND (:defeituoso IS NULL OR t.defeituoso = :defeituoso)")
    List<Tijolo> filtrar(@Param("status") StatusTijolo status,
                         @Param("cor") String cor,
                         @Param("defeituoso") Boolean defeituoso);
}
