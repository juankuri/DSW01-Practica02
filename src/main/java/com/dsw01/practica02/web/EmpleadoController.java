package com.dsw01.practica02.web;

import com.dsw01.practica02.service.EmpleadoService;
import com.dsw01.practica02.web.dto.EmpleadoCreateRequest;
import com.dsw01.practica02.web.dto.EmpleadoResponse;
import com.dsw01.practica02.web.dto.EmpleadoUpdateRequest;
import com.dsw01.practica02.web.dto.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({ "/api/empleados", "/api/v1/empleados" })
@Tag(name = "Empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping
    @Operation(summary = "Crear empleado")
    public ResponseEntity<EmpleadoResponse> crear(@Valid @RequestBody EmpleadoCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.crear(request));
    }

    @GetMapping("/{clave}")
    @Operation(summary = "Obtener empleado por clave")
    public ResponseEntity<EmpleadoResponse> obtenerPorClave(@PathVariable String clave) {
        return ResponseEntity.ok(empleadoService.obtenerPorClave(clave));
    }

    @GetMapping
    @Operation(summary = "Listar empleados")
    public ResponseEntity<List<EmpleadoResponse>> listar() {
        return ResponseEntity.ok(empleadoService.listar());
    }

    @GetMapping(params = { "page", "size" })
    @Operation(summary = "Listar empleados paginados")
    public ResponseEntity<PagedResponse<EmpleadoResponse>> listarPaginado(@RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(empleadoService.listarPaginado(page, size));
    }

    @PutMapping("/{clave}")
    @Operation(summary = "Actualizar empleado")
    public ResponseEntity<EmpleadoResponse> actualizar(@PathVariable String clave,
            @Valid @RequestBody EmpleadoUpdateRequest request) {
        return ResponseEntity.ok(empleadoService.actualizar(clave, request));
    }

    @DeleteMapping("/{clave}")
    @Operation(summary = "Eliminar empleado")
    public ResponseEntity<Void> eliminar(@PathVariable String clave) {
        empleadoService.eliminar(clave);
        return ResponseEntity.noContent().build();
    }
}
