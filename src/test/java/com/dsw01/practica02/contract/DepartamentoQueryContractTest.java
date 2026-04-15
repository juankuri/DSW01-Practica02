package com.dsw01.practica02.contract;

import com.dsw01.practica02.service.DepartamentoService;
import com.dsw01.practica02.service.EmpleadoService;
import com.dsw01.practica02.service.exception.RecursoNoEncontradoException;
import com.dsw01.practica02.web.dto.DepartamentoResponse;
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
class DepartamentoQueryContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartamentoService departamentoService;

    @MockBean
    private EmpleadoService empleadoService;

    @Test
    void getByClaveShouldReturn200() throws Exception {
        DepartamentoResponse response = new DepartamentoResponse();
        response.setClave("D001");
        when(departamentoService.obtenerPorClave("D001")).thenReturn(response);

        mockMvc.perform(get("/api/departamentos/D001")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123")))
                .andExpect(status().isOk());
    }

    @Test
    void getByClaveMissingShouldReturn404() throws Exception {
        when(departamentoService.obtenerPorClave("NOPE")).thenThrow(new RecursoNoEncontradoException("not found"));

        mockMvc.perform(get("/api/departamentos/NOPE")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123")))
                .andExpect(status().isNotFound());
    }

    @Test
    void listShouldReturn200() throws Exception {
        when(departamentoService.listar()).thenReturn(List.of());

        mockMvc.perform(get("/api/departamentos")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123")))
                .andExpect(status().isOk());
    }

    @Test
    void versionedListShouldReturn200() throws Exception {
        when(departamentoService.listar()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/departamentos")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123")))
                .andExpect(status().isOk());
    }

    @Test
    void pagedListShouldReturn200() throws Exception {
        when(departamentoService.listarPaginado(0, 10)).thenReturn(new PagedResponse<>(List.of(), 0, 10, 0, 0));

        mockMvc.perform(get("/api/v1/departamentos?page=0&size=10")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123")))
                .andExpect(status().isOk());
    }
}
