package com.dsw01.practica02.service;

import com.dsw01.practica02.domain.Departamento;
import com.dsw01.practica02.repository.DepartamentoRepository;
import com.dsw01.practica02.service.exception.ConflictoNegocioException;
import com.dsw01.practica02.service.exception.RecursoNoEncontradoException;
import com.dsw01.practica02.web.dto.DepartamentoCreateRequest;
import com.dsw01.practica02.web.dto.DepartamentoResponse;
import com.dsw01.practica02.web.dto.DepartamentoUpdateRequest;
import com.dsw01.practica02.web.dto.PagedResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    @Transactional
    public DepartamentoResponse crear(DepartamentoCreateRequest request) {
        String clave = sanitize(request.getClave());
        if (departamentoRepository.existsById(clave)) {
            throw new ConflictoNegocioException("La clave ya existe");
        }
        Departamento d = new Departamento();
        d.setClave(clave);
        d.setNombre(sanitize(request.getNombre()));
        validateLengths(d);
        return toResponse(departamentoRepository.save(d));
    }

    @Transactional(readOnly = true)
    public DepartamentoResponse obtenerPorClave(String clave) {
        Departamento d = departamentoRepository.findById(sanitize(clave))
                .orElseThrow(() -> new RecursoNoEncontradoException("Departamento no encontrado"));
        return toResponse(d);
    }

    @Transactional(readOnly = true)
    public List<DepartamentoResponse> listar() {
        return departamentoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PagedResponse<DepartamentoResponse> listarPaginado(int page, int size) {
        var pageResult = departamentoRepository.findAll(PageRequest.of(page, size));
        return new PagedResponse<>(
                pageResult.getContent().stream().map(this::toResponse).toList(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages());
    }

    @Transactional
    public DepartamentoResponse actualizar(String clave, DepartamentoUpdateRequest request) {
        Departamento d = departamentoRepository.findById(sanitize(clave))
                .orElseThrow(() -> new RecursoNoEncontradoException("Departamento no encontrado"));
        d.setNombre(sanitize(request.getNombre()));
        validateLengths(d);
        return toResponse(departamentoRepository.save(d));
    }

    @Transactional
    public void eliminar(String clave) {
        departamentoRepository.findById(sanitize(clave)).ifPresent(departamentoRepository::delete);
    }

    private DepartamentoResponse toResponse(Departamento d) {
        DepartamentoResponse r = new DepartamentoResponse();
        r.setClave(d.getClave());
        r.setNombre(d.getNombre());
        return r;
    }

    private String sanitize(String value) {
        if (value == null)
            return "";
        return value.trim();
    }

    private void validateLengths(Departamento d) {
        if (d.getClave() == null || d.getClave().isBlank())
            throw new IllegalArgumentException("clave es obligatorio");
        if (d.getNombre() == null || d.getNombre().isBlank())
            throw new IllegalArgumentException("nombre es obligatorio");
        if (d.getClave().length() > 100)
            throw new IllegalArgumentException("clave excede longitud maxima 100");
        if (d.getNombre().length() > 100)
            throw new IllegalArgumentException("nombre excede longitud maxima 100");
    }
}
