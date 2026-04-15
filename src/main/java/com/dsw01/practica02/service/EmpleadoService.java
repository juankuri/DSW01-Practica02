package com.dsw01.practica02.service;

import com.dsw01.practica02.domain.Empleado;
import com.dsw01.practica02.repository.EmpleadoRepository;
import com.dsw01.practica02.repository.DepartamentoRepository;
import com.dsw01.practica02.service.exception.ConflictoNegocioException;
import com.dsw01.practica02.service.exception.RecursoNoEncontradoException;
import com.dsw01.practica02.web.dto.EmpleadoCreateRequest;
import com.dsw01.practica02.web.dto.PagedResponse;
import com.dsw01.practica02.web.dto.EmpleadoResponse;
import com.dsw01.practica02.web.dto.EmpleadoUpdateRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository, DepartamentoRepository departamentoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    @Transactional
    public EmpleadoResponse crear(EmpleadoCreateRequest request) {
        String clave = sanitize(request.getClave());
        if (empleadoRepository.existsById(clave)) {
            throw new ConflictoNegocioException("La clave ya existe");
        }
        Empleado empleado = new Empleado();
        empleado.setClave(clave);
        empleado.setNombre(sanitize(request.getNombre()));
        empleado.setDireccion(sanitize(request.getDireccion()));
        empleado.setTelefono(sanitize(request.getTelefono()));

        String deptClave = sanitize(request.getDepartamentoClave());
        if (!deptClave.isBlank()) {
            var dept = departamentoRepository.findById(deptClave)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Departamento no encontrado"));
            empleado.setDepartamento(dept);
        }

        validateLengths(empleado);
        return toResponse(empleadoRepository.save(empleado));
    }

    @Transactional(readOnly = true)
    public EmpleadoResponse obtenerPorClave(String clave) {
        Empleado empleado = empleadoRepository.findById(sanitize(clave))
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado"));
        return toResponse(empleado);
    }

    @Transactional(readOnly = true)
    public List<EmpleadoResponse> listar() {
        return empleadoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PagedResponse<EmpleadoResponse> listarPaginado(int page, int size) {
        var pageResult = empleadoRepository.findAll(PageRequest.of(page, size));
        return new PagedResponse<>(
                pageResult.getContent().stream().map(this::toResponse).toList(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages());
    }

    @Transactional
    public EmpleadoResponse actualizar(String clave, EmpleadoUpdateRequest request) {
        Empleado empleado = empleadoRepository.findById(sanitize(clave))
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado"));

        if (!empleado.getVersion().equals(request.getVersion())) {
            throw new ConflictoNegocioException("Version desactualizada");
        }

        empleado.setNombre(sanitize(request.getNombre()));
        empleado.setDireccion(sanitize(request.getDireccion()));
        empleado.setTelefono(sanitize(request.getTelefono()));
        String deptClave = sanitize(request.getDepartamentoClave());
        if (!deptClave.isBlank()) {
            var dept = departamentoRepository.findById(deptClave)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Departamento no encontrado"));
            empleado.setDepartamento(dept);
        } else {
            empleado.setDepartamento(null);
        }
        validateLengths(empleado);
        return toResponse(empleadoRepository.save(empleado));
    }

    @Transactional
    public void eliminar(String clave) {
        empleadoRepository.findById(sanitize(clave)).ifPresent(empleadoRepository::delete);
    }

    private EmpleadoResponse toResponse(Empleado empleado) {
        EmpleadoResponse response = new EmpleadoResponse();
        response.setClave(empleado.getClave());
        response.setNombre(empleado.getNombre());
        response.setDireccion(empleado.getDireccion());
        response.setTelefono(empleado.getTelefono());
        if (empleado.getDepartamento() != null) {
            response.setDepartamentoClave(empleado.getDepartamento().getClave());
        }
        response.setVersion(empleado.getVersion());
        return response;
    }

    private String sanitize(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }

    private void validateLengths(Empleado empleado) {
        validateField("clave", empleado.getClave(), 100);
        validateField("nombre", empleado.getNombre(), 100);
        validateField("direccion", empleado.getDireccion(), 100);
        validateField("telefono", empleado.getTelefono(), 100);
    }

    private void validateField(String field, String value, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " es obligatorio");
        }
        if (value.length() > maxLength) {
            throw new IllegalArgumentException(field + " excede longitud maxima " + maxLength);
        }
    }
}
