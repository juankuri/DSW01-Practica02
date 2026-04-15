package com.dsw01.practica02.integration;

import com.dsw01.practica02.repository.EmpleadoRepository;
import com.dsw01.practica02.service.EmpleadoService;
import com.dsw01.practica02.service.exception.RecursoNoEncontradoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpleadoQueryIntegrationTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void missingClaveShouldThrowNotFound() {
        when(empleadoRepository.findById("NOPE")).thenReturn(Optional.empty());
        assertThrows(RecursoNoEncontradoException.class, () -> empleadoService.obtenerPorClave("NOPE"));
    }
}
