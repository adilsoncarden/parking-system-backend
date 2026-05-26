package com.condosaas.api.entrada.service;

import com.condosaas.api.condominio.Repository.CondominioRepository;
import com.condosaas.api.condominio.entity.Condominio;
import com.condosaas.api.entrada.dto.EntradaSalidaRequest;
import com.condosaas.api.entrada.dto.EntradaSalidaResponse;
import com.condosaas.api.entrada.entity.EntradaSalida;
import com.condosaas.api.entrada.repository.EntradaSalidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntradaSalidaService {

    private final EntradaSalidaRepository entradaSalidaRepository;
    private final CondominioRepository condominioRepository;

    public List<EntradaSalidaResponse> listarTodos() {
        return entradaSalidaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<EntradaSalidaResponse> listarPorCondominio(Long idCondominio) {
        return entradaSalidaRepository.findByCondominioId(idCondominio)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public EntradaSalidaResponse obtenerPorId(Long id) {
        EntradaSalida entrada = entradaSalidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada/Salida no encontrada"));
        return toResponse(entrada);
    }

    public EntradaSalidaResponse crear(EntradaSalidaRequest request) {
        Condominio condominio = condominioRepository.findById(request.getIdCondominio())
                .orElseThrow(() -> new RuntimeException("Condominio no encontrado"));
        EntradaSalida entrada = new EntradaSalida();
        entrada.setNombre(request.getNombre());
        entrada.setCondominio(condominio);
        return toResponse(entradaSalidaRepository.save(entrada));
    }

    public EntradaSalidaResponse actualizar(Long id, EntradaSalidaRequest request) {
        EntradaSalida entrada = entradaSalidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada/Salida no encontrada"));
        Condominio condominio = condominioRepository.findById(request.getIdCondominio())
                .orElseThrow(() -> new RuntimeException("Condominio no encontrado"));
        entrada.setNombre(request.getNombre());
        entrada.setCondominio(condominio);
        return toResponse(entradaSalidaRepository.save(entrada));
    }

    public void eliminar(Long id) {
        entradaSalidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada/Salida no encontrada"));
        entradaSalidaRepository.deleteById(id);
    }

    private EntradaSalidaResponse toResponse(EntradaSalida e) {
        EntradaSalidaResponse response = new EntradaSalidaResponse();
        response.setId(e.getId());
        response.setNombre(e.getNombre());
        response.setIdCondominio(e.getCondominio().getId());
        response.setNombreCondominio(e.getCondominio().getNombre());
        return response;
    }
}