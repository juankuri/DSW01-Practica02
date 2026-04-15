package com.dsw01.practica02.web;

import com.dsw01.practica02.service.DepartamentoService;
import com.dsw01.practica02.web.dto.DepartamentoCreateRequest;
import com.dsw01.practica02.web.dto.DepartamentoResponse;
import com.dsw01.practica02.web.dto.DepartamentoUpdateRequest;
import com.dsw01.practica02.web.dto.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({ "/api/departamentos", "/api/v1/departamentos" })
@Tag(name = "Departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @PostMapping
    @Operation(summary = "Crear departamento")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartamentoResponse crear(@Valid @RequestBody DepartamentoCreateRequest request) {
        return departamentoService.crear(request);
    }

    @GetMapping
    @Operation(summary = "Listar departamentos")
    public List<DepartamentoResponse> listar() {
        return departamentoService.listar();
    }

    @GetMapping(params = { "page", "size" })
    @Operation(summary = "Listar departamentos paginados")
    public PagedResponse<DepartamentoResponse> listarPaginado(@RequestParam int page,
            @RequestParam int size) {
        return departamentoService.listarPaginado(page, size);
    }

    @GetMapping("/{clave}")
    @Operation(summary = "Obtener departamento por clave")
    public DepartamentoResponse obtener(@PathVariable String clave) {
        return departamentoService.obtenerPorClave(clave);
    }

    @PutMapping("/{clave}")
    @Operation(summary = "Actualizar departamento")
    public DepartamentoResponse actualizar(@PathVariable String clave,
            @Valid @RequestBody DepartamentoUpdateRequest request) {
        return departamentoService.actualizar(clave, request);
    }

    @DeleteMapping("/{clave}")
    @Operation(summary = "Eliminar departamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String clave) {
        departamentoService.eliminar(clave);
    }
}
