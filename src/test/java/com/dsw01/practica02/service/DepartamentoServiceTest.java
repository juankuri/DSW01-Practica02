package com.dsw01.practica02.service;

import com.dsw01.practica02.domain.Departamento;
import com.dsw01.practica02.repository.DepartamentoRepository;
import com.dsw01.practica02.service.exception.ConflictoNegocioException;
import com.dsw01.practica02.service.exception.RecursoNoEncontradoException;
import com.dsw01.practica02.web.dto.DepartamentoCreateRequest;
import com.dsw01.practica02.web.dto.DepartamentoUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartamentoServiceTest {

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private DepartamentoService departamentoService;

    @Test
    void crearDuplicateClaveThrows() {
        DepartamentoCreateRequest req = new DepartamentoCreateRequest();
        req.setClave("D001");
        req.setNombre("Ventas");

        when(departamentoRepository.existsById("D001")).thenReturn(true);

        assertThrows(ConflictoNegocioException.class, () -> departamentoService.crear(req));
    }

    @Test
    void obtenerNonExistingThrows() {
        when(departamentoRepository.findById("NOPE")).thenReturn(Optional.empty());
        assertThrows(RecursoNoEncontradoException.class, () -> departamentoService.obtenerPorClave("NOPE"));
    }
}
