package com.dsw01.practica02.contract;

import com.dsw01.practica02.service.DepartamentoService;
import com.dsw01.practica02.service.EmpleadoService;
import com.dsw01.practica02.service.exception.ConflictoNegocioException;
import com.dsw01.practica02.web.dto.DepartamentoResponse;
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
class DepartamentoCreateContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartamentoService departamentoService;

    @MockBean
    private EmpleadoService empleadoService;

    @Test
    void createShouldReturn201() throws Exception {
        DepartamentoResponse response = new DepartamentoResponse();
        response.setClave("D001");
        when(departamentoService.crear(any())).thenReturn(response);

        mockMvc.perform(post("/api/departamentos")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clave\":\"D001\",\"nombre\":\"Ventas\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void duplicateShouldReturn409() throws Exception {
        when(departamentoService.crear(any())).thenThrow(new ConflictoNegocioException("duplicado"));

        mockMvc.perform(post("/api/departamentos")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clave\":\"D001\",\"nombre\":\"Ventas\"}"))
                .andExpect(status().isConflict());
    }
}
