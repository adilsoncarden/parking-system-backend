package com.condosaas.api.condominio.service;

import java.util.List;
import java.util.stream.Collectors;

import com.condosaas.api.condominio.Repository.CondominioRepository;
import com.condosaas.api.condominio.entity.Condominio;
import org.springframework.stereotype.Service;

import com.condosaas.api.condominio.Controller.dto.CondominioRequest;
import com.condosaas.api.condominio.Controller.dto.CondominioResponse;

@Service
public class CondominioService {

    private final CondominioRepository condominioRepository;

    public CondominioService(CondominioRepository condominioRepository) {
        this.condominioRepository = condominioRepository;
    }

    public List<CondominioResponse> listarTodos() {
        return condominioRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CondominioResponse obtenerPorId(Long id) {
        Condominio condo = condominioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condominio no encontrado con id: " + id));
        return toResponse(condo);
    }

    public CondominioResponse crear(CondominioRequest request) {
        Condominio condo = new Condominio();
        condo.setNombre(request.getNombre());
        condo.setDireccion(request.getDireccion());
        condo.setTipo(request.getTipo());
        condo.setImagen(request.getImagen());
        condo.setLatitud(request.getLatitud());
        condo.setLongitud(request.getLongitud());
        condo.setNumEntradas(request.getNumEntradas() != null ? request.getNumEntradas() : 1);

        Condominio guardado = condominioRepository.save(condo);
        return toResponse(guardado);
    }

    public CondominioResponse actualizar(Long id, CondominioRequest request) {
        Condominio existente = condominioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condominio no encontrado con id: " + id));

        existente.setNombre(request.getNombre());
        existente.setDireccion(request.getDireccion());
        existente.setTipo(request.getTipo());
        existente.setImagen(request.getImagen());
        existente.setLatitud(request.getLatitud());
        existente.setLongitud(request.getLongitud());
        existente.setNumEntradas(request.getNumEntradas() != null ? request.getNumEntradas() : 1);

        Condominio actualizado = condominioRepository.save(existente);
        return toResponse(actualizado);
    }

    public void eliminar(Long id) {
        if (!condominioRepository.existsById(id)) {
            throw new RuntimeException("Condominio no encontrado con id: " + id);
        }
        condominioRepository.deleteById(id);
    }

    private CondominioResponse toResponse(Condominio c) {
        CondominioResponse r = new CondominioResponse();
        r.setId(c.getId());
        r.setNombre(c.getNombre());
        r.setDireccion(c.getDireccion());
        r.setTipo(c.getTipo());
        r.setImagen(c.getImagen());
        r.setLatitud(c.getLatitud());
        r.setLongitud(c.getLongitud());
        r.setNumEntradas(c.getNumEntradas());
        r.setCreatedAt(c.getCreatedAt());
        r.setUpdatedAt(c.getUpdatedAt());
        return r;
    }
}