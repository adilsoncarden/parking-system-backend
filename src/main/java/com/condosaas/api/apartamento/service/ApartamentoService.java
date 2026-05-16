package com.condosaas.api.apartamento.service;

import com.condosaas.api.apartamento.dto.ApartamentoRequest;
import com.condosaas.api.apartamento.dto.ApartamentoResponse;
import com.condosaas.api.apartamento.entity.Apartamento;
import com.condosaas.api.apartamento.repository.ApartamentoRepository;
import com.condosaas.api.piso.entity.Piso;
import com.condosaas.api.piso.repository.PisoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApartamentoService {

    private final ApartamentoRepository apartamentoRepository;
    private final PisoRepository pisoRepository;

    public List<ApartamentoResponse> listarTodos() {
        return apartamentoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ApartamentoResponse> listarPorPiso(Long idPiso) {
        return apartamentoRepository.findByPisoId(idPiso)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ApartamentoResponse obtenerPorId(Long id) {
        Apartamento apartamento = apartamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apartamento no encontrado"));
        return toResponse(apartamento);
    }

    public ApartamentoResponse crear(ApartamentoRequest request) {
        Piso piso = pisoRepository.findById(request.getIdPiso())
                .orElseThrow(() -> new RuntimeException("Piso no encontrado"));

        Apartamento apartamento = new Apartamento();
        apartamento.setNumeroApartamento(request.getNumero());
        apartamento.setPropietario(request.getPropietario());
        apartamento.setEstado(request.getEstado());
        apartamento.setPiso(piso);

        return toResponse(apartamentoRepository.save(apartamento));
    }

    public ApartamentoResponse actualizar(Long id, ApartamentoRequest request) {
        Apartamento apartamento = apartamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apartamento no encontrado"));
        
        Piso piso = pisoRepository.findById(request.getIdPiso())
                .orElseThrow(() -> new RuntimeException("Piso no encontrado"));

        apartamento.setNumeroApartamento(request.getNumero());
        apartamento.setPropietario(request.getPropietario());
        apartamento.setEstado(request.getEstado());
        apartamento.setPiso(piso);

        return toResponse(apartamentoRepository.save(apartamento));
    }

    public void eliminar(Long id) {
        apartamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apartamento no encontrado"));
        apartamentoRepository.deleteById(id);
    }

    // Helper method para mapear la entidad a un DTO de respuesta limpio
    private ApartamentoResponse toResponse(Apartamento apartamento) {
        ApartamentoResponse response = new ApartamentoResponse();
        response.setId(apartamento.getId());
        response.setNumero(apartamento.getNumeroApartamento());
        response.setIdPiso(apartamento.getPiso().getId());
        response.setNumeroPiso(apartamento.getPiso().getNumeroPiso());
        
        // Verificación de seguridad en caso de que la torre no esté cargada
        if(apartamento.getPiso().getTorre() != null) {
            response.setNombreTorre(apartamento.getPiso().getTorre().getNombre());
        }
        
        response.setPropietario(apartamento.getPropietario());
        response.setEstado(apartamento.getEstado());
        response.setCreatedAt(apartamento.getCreatedAt());
        return response;
    }
}