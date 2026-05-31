package com.condosaas.api.module.dashboard.controller;

import com.condosaas.api.module.apartamento.model.EstadoApartamento;
import com.condosaas.api.module.apartamento.repository.ApartamentoRepository;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.dashboard.dto.DashboardStatsDTO;
import com.condosaas.api.module.piso.repository.PisoRepository;
import com.condosaas.api.module.torre.repository.TorreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final CondominioRepository condominioRepository;
    private final TorreRepository torreRepository;
    private final PisoRepository pisoRepository;
    private final ApartamentoRepository apartamentoRepository;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getStats() {
        long totalCondominios = condominioRepository.count();
        long totalTorres = torreRepository.count();
        long totalPisos = pisoRepository.count();
        long totalApartamentos = apartamentoRepository.count();

        long disponibles = apartamentoRepository.countByEstado(EstadoApartamento.DISPONIBLE);
        long ocupados = apartamentoRepository.countByEstado(EstadoApartamento.OCUPADO);
        long mantenimiento = apartamentoRepository.countByEstado(EstadoApartamento.MANTENIMIENTO);
        long inactivos = apartamentoRepository.countByEstado(EstadoApartamento.INACTIVO);

        return ResponseEntity.ok(DashboardStatsDTO.builder()
                .totalCondominios(totalCondominios)
                .totalTorres(totalTorres)
                .totalPisos(totalPisos)
                .totalApartamentos(totalApartamentos)
                .apartamentosDisponibles(disponibles)
                .apartamentosOcupados(ocupados)
                .apartamentosMantenimiento(mantenimiento)
                .apartamentosInactivos(inactivos)
                .build());
    }
}
