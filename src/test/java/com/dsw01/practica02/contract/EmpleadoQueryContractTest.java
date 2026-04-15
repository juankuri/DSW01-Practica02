package com.dsw01.practica02.contract;

import com.dsw01.practica02.service.EmpleadoService;
import com.dsw01.practica02.service.exception.RecursoNoEncontradoException;
import com.dsw01.practica02.web.dto.EmpleadoResponse;
import com.dsw01.practica02.web.dto.PagedResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmpleadoQueryContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpleadoService empleadoService;

    @MockBean
    private com.dsw01.practica02.service.DepartamentoService departamentoService;

    @Test
    void getByClaveShouldReturn200() throws Exception {
        EmpleadoResponse response = new EmpleadoResponse();
        response.setClave("EMP001");
        when(empleadoService.obtenerPorClave("EMP001")).thenReturn(response);

        mockMvc.perform(get("/api/empleados/EMP001")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123")))
                .andExpect(status().isOk());
    }

    @Test
    void getByClaveMissingShouldReturn404() throws Exception {
        when(empleadoService.obtenerPorClave("NOPE")).thenThrow(new RecursoNoEncontradoException("not found"));

        mockMvc.perform(get("/api/empleados/NOPE")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123")))
                .andExpect(status().isNotFound());
    }

    @Test
    void listShouldReturn200() throws Exception {
        when(empleadoService.listar()).thenReturn(List.of());

        mockMvc.perform(get("/api/empleados")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123")))
                .andExpect(status().isOk());
    }

    @Test
    void versionedListShouldReturn200() throws Exception {
        when(empleadoService.listar()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/empleados")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123")))
                .andExpect(status().isOk());
    }

    @Test
    void pagedListShouldReturn200() throws Exception {
        when(empleadoService.listarPaginado(0, 10)).thenReturn(new PagedResponse<>(List.of(), 0, 10, 0, 0));

        mockMvc.perform(get("/api/v1/empleados?page=0&size=10")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123")))
                .andExpect(status().isOk());
    }
}
