package com.fabrica.service;

import com.fabrica.model.StatusTijolo;
import com.fabrica.model.Tijolo;
import com.fabrica.repository.TijoloRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TijoloServiceImplTest {

    @Mock
    private TijoloRepository repository;

    @InjectMocks
    private TijoloServiceImpl service;

    @Test
    void deveAprovarTijoloComDefeitoAleatoriamente() {
        // given
        Tijolo tijolo = new Tijolo();
        tijolo.setId(1L);
        tijolo.setStatus(StatusTijolo.EM_INSPECAO);
        tijolo.setDefeituoso(null);

        when(repository.findById(1L)).thenReturn(Optional.of(tijolo));
        when(repository.save(any(Tijolo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Tijolo atualizado = service.alterarStatus(1L, StatusTijolo.APROVADO);

        // then
        assertEquals(StatusTijolo.APROVADO, atualizado.getStatus());
        assertNotNull(atualizado.getDefeituoso()); // pode ser true ou false
    }

    @Test
    void deveDeletarTijoloDefeituoso() {
        // given
        Tijolo tijolo = new Tijolo();
        tijolo.setId(2L);
        tijolo.setDefeituoso(true);

        when(repository.findById(2L)).thenReturn(Optional.of(tijolo));

        // when
        service.deletarSeDefeituoso(2L);

        // then
        verify(repository).delete(tijolo);
    }

    @Test
    void naoDeveDeletarTijoloSemDefeito() {
        // given
        Tijolo tijolo = new Tijolo();
        tijolo.setId(3L);
        tijolo.setDefeituoso(false);

        when(repository.findById(3L)).thenReturn(Optional.of(tijolo));

        // then
        assertThrows(RuntimeException.class, () -> service.deletarSeDefeituoso(3L));
        verify(repository, never()).delete(tijolo);
    }
}