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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmpleadoUpdateDeleteContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpleadoService empleadoService;

        @MockBean
        private com.dsw01.practica02.service.DepartamentoService departamentoService;

    @Test
    void updateStaleShouldReturn409() throws Exception {
        when(empleadoService.actualizar(any(), any())).thenThrow(new ConflictoNegocioException("stale"));

        mockMvc.perform(put("/api/empleados/EMP001")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123"))
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"A\",\"direccion\":\"B\",\"telefono\":\"C\",\"version\":0}"))
                .andExpect(status().isConflict());
    }

    @Test
    void updateShouldReturn200() throws Exception {
        EmpleadoResponse response = new EmpleadoResponse();
        response.setClave("EMP001");
        when(empleadoService.actualizar(any(), any())).thenReturn(response);

        mockMvc.perform(put("/api/empleados/EMP001")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123"))
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"A\",\"direccion\":\"B\",\"telefono\":\"C\",\"version\":0}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteShouldReturn204() throws Exception {
        doNothing().when(empleadoService).eliminar("NOPE");

        mockMvc.perform(delete("/api/empleados/NOPE")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
    }
}
