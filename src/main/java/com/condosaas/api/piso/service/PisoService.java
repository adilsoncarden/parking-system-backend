package com.condosaas.api.piso.service;

import com.condosaas.api.piso.dto.PisoRequest;
import com.condosaas.api.piso.dto.PisoResponse;
import com.condosaas.api.piso.entity.Piso;
import com.condosaas.api.piso.repository.PisoRepository;
import com.condosaas.api.torres.entity.Torres;
import com.condosaas.api.torres.repository.TorresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PisoService {

    private final PisoRepository pisoRepository;
    private final TorresRepository torresRepository;

    public List<PisoResponse> listarTodos() {
        return pisoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<PisoResponse> listarPorTorre(Long idTorre) {
        return pisoRepository.findByTorreIdTorres(idTorre)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PisoResponse obtenerPorId(Long id) {
        Piso piso = pisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piso no encontrado"));
        return toResponse(piso);
    }

    public PisoResponse crear(PisoRequest request) {
        Torres torre = torresRepository.findById(request.getIdTorre())
                .orElseThrow(() -> new RuntimeException("Torre no encontrada"));
        Piso piso = new Piso();
        piso.setNumeroPiso(request.getNumeroPiso());
        piso.setTorre(torre);
        return toResponse(pisoRepository.save(piso));
    }

    public PisoResponse actualizar(Long id, PisoRequest request) {
        Piso piso = pisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piso no encontrado"));
        Torres torre = torresRepository.findById(request.getIdTorre())
                .orElseThrow(() -> new RuntimeException("Torre no encontrada"));
        piso.setNumeroPiso(request.getNumeroPiso());
        piso.setTorre(torre);
        return toResponse(pisoRepository.save(piso));
    }

    public void eliminar(Long id) {
        pisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piso no encontrado"));
        pisoRepository.deleteById(id);
    }

    private PisoResponse toResponse(Piso piso) {
        PisoResponse response = new PisoResponse();
        response.setId(piso.getId());
        response.setNumeroPiso(piso.getNumeroPiso());
        response.setIdTorre(piso.getTorre().getIdTorres());
        response.setNombreTorre(piso.getTorre().getNombre());
        return response;
    }
}