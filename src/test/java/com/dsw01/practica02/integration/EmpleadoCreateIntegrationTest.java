package com.dsw01.practica02.integration;

import com.dsw01.practica02.repository.EmpleadoRepository;
import com.dsw01.practica02.service.EmpleadoService;
import com.dsw01.practica02.service.exception.ConflictoNegocioException;
import com.dsw01.practica02.web.dto.EmpleadoCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpleadoCreateIntegrationTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private com.dsw01.practica02.repository.DepartamentoRepository departamentoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void duplicateClaveShouldThrowConflict() {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setClave("EMP001");
        request.setNombre("Ana");
        request.setDireccion("Calle");
        request.setTelefono("555");

        when(empleadoRepository.existsById("EMP001")).thenReturn(true);

        assertThrows(ConflictoNegocioException.class, () -> empleadoService.crear(request));
    }
}
