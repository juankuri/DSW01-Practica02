package com.dsw01.practica02.integration;

import com.dsw01.practica02.domain.Empleado;
import com.dsw01.practica02.repository.EmpleadoRepository;
import com.dsw01.practica02.service.EmpleadoService;
import com.dsw01.practica02.service.exception.ConflictoNegocioException;
import com.dsw01.practica02.web.dto.EmpleadoUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpleadoConcurrencyIntegrationTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void staleVersionShouldThrowConflict() {
        Empleado empleado = new Empleado();
        empleado.setClave("EMP001");
        empleado.setVersion(3L);

        EmpleadoUpdateRequest request = new EmpleadoUpdateRequest();
        request.setNombre("Ana");
        request.setDireccion("Calle");
        request.setTelefono("555");
        request.setVersion(2L);

        when(empleadoRepository.findById("EMP001")).thenReturn(Optional.of(empleado));

        assertThrows(ConflictoNegocioException.class, () -> empleadoService.actualizar("EMP001", request));
    }
}
