package com.dsw01.practica02.contract;

import com.dsw01.practica02.service.EmpleadoService;
import com.dsw01.practica02.service.exception.ConflictoNegocioException;
import com.dsw01.practica02.web.dto.EmpleadoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmpleadoCreateContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpleadoService empleadoService;
    
        @MockBean
        private com.dsw01.practica02.service.DepartamentoService departamentoService;

    @Test
    void createShouldReturn201() throws Exception {
        EmpleadoResponse response = new EmpleadoResponse();
        response.setClave("EMP001");
        when(empleadoService.crear(any())).thenReturn(response);

        mockMvc.perform(post("/api/empleados")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123"))
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clave\":\"EMP001\",\"nombre\":\"Ana\",\"direccion\":\"Calle\",\"telefono\":\"555\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void duplicateShouldReturn409() throws Exception {
        when(empleadoService.crear(any())).thenThrow(new ConflictoNegocioException("duplicado"));

        mockMvc.perform(post("/api/empleados")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123"))
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clave\":\"EMP001\",\"nombre\":\"Ana\",\"direccion\":\"Calle\",\"telefono\":\"555\"}"))
                .andExpect(status().isConflict());
    }
}
