package com.dsw01.practica02.contract;

import com.dsw01.practica02.service.DepartamentoService;
import com.dsw01.practica02.service.EmpleadoService;
import com.dsw01.practica02.web.dto.DepartamentoResponse;
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
class DepartamentoUpdateDeleteContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartamentoService departamentoService;

    @MockBean
    private EmpleadoService empleadoService;

    @Test
    void updateShouldReturn200() throws Exception {
        DepartamentoResponse response = new DepartamentoResponse();
        response.setClave("D001");
        when(departamentoService.actualizar(any(), any())).thenReturn(response);

        mockMvc.perform(put("/api/departamentos/D001")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Ventas\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteShouldReturn204() throws Exception {
        doNothing().when(departamentoService).eliminar("NOPE");

        mockMvc.perform(delete("/api/departamentos/NOPE")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
    }
}
