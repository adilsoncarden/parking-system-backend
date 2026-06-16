package com.condosaas.api.module.dashboard.controller;

import com.condosaas.api.module.apartamento.model.EstadoApartamento;
import com.condosaas.api.module.apartamento.repository.ApartamentoRepository;
import com.condosaas.api.module.carrito_carga.model.EstadoCarrito;
import com.condosaas.api.module.carrito_carga.repository.CarritoCargaRepository;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.dashboard.dto.DashboardCarritosDTO;
import com.condosaas.api.module.dashboard.dto.DashboardCarritosDTO.EstadisticasCarrito;
import com.condosaas.api.module.dashboard.dto.DashboardStatsDTO;
import com.condosaas.api.module.log_prestamo_carrito.model.EstadoPrestamo;
import com.condosaas.api.module.log_prestamo_carrito.repository.LogPrestamoCarritoRepository;
import com.condosaas.api.module.piso.repository.PisoRepository;
import com.condosaas.api.module.torre.repository.TorreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.condosaas.api.module.dashboard.dto.DashboardParkingDTO;
import com.condosaas.api.module.estacionamiento.model.EstadoOcupacion;
import com.condosaas.api.module.estacionamiento.repository.EstacionamientoRepository;
import com.condosaas.api.module.log_acceso_vehicular.repository.LogAccesoVehicularRepository;
import com.condosaas.api.module.permanencia_activa.model.EstadoPermanencia;
import com.condosaas.api.module.permanencia_activa.repository.PermanenciaActivaRepository;
import com.condosaas.api.module.vehiculo.repository.VehiculoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final CondominioRepository condominioRepository;
    private final TorreRepository torreRepository;
    private final PisoRepository pisoRepository;
    private final ApartamentoRepository apartamentoRepository;
    private final CarritoCargaRepository carritoCargaRepository;
    private final LogPrestamoCarritoRepository prestamoCarritoRepository;
    private final EstacionamientoRepository estacionamientoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final PermanenciaActivaRepository permanenciaActivaRepository;
    private final LogAccesoVehicularRepository logAccesoVehicularRepository;

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

    @GetMapping("/parking")
    public ResponseEntity<DashboardParkingDTO> getParkingStats() {
        long totalPlazas = estacionamientoRepository.count();
        long libres = estacionamientoRepository.countByEstadoOcupacion(EstadoOcupacion.LIBRE);
        long ocupadas = estacionamientoRepository.countByEstadoOcupacion(EstadoOcupacion.OCUPADO);
        long reservadas = estacionamientoRepository.countByEstadoOcupacion(EstadoOcupacion.RESERVADO);
        long inactivas = estacionamientoRepository.countByEstadoOcupacion(EstadoOcupacion.INACTIVO);

        long totalVehiculos = vehiculoRepository.count();
        long permanenciasActivas = permanenciaActivaRepository.countByEstado(EstadoPermanencia.ACTIVA);
        long accesosHoy = logAccesoVehicularRepository
                .countByFechaHoraGreaterThanEqual(LocalDate.now().atStartOfDay());

        return ResponseEntity.ok(DashboardParkingDTO.builder()
                .totalPlazas(totalPlazas)
                .plazasLibres(libres)
                .plazasOcupadas(ocupadas)
                .plazasReservadas(reservadas)
                .plazasInactivas(inactivas)
                .totalVehiculos(totalVehiculos)
                .permanenciasActivas(permanenciasActivas)
                .accesosHoy(accesosHoy)
                .build());
    }

    @GetMapping("/carritos")
    public ResponseEntity<DashboardCarritosDTO> getCarritosStats() {
        try {
            long total = carritoCargaRepository.count();
            long disponibles = carritoCargaRepository.countByEstado(EstadoCarrito.DISPONIBLE);
            long prestados = carritoCargaRepository.countByEstado(EstadoCarrito.PRESTADO);
            long prestamosActivos = prestamoCarritoRepository.countByEstado(EstadoPrestamo.ACTIVO);
            long penalizados = prestamoCarritoRepository.countByPenalizadoTrueAndPagadoFalse();
            BigDecimal totalRecaudado = prestamoCarritoRepository.sumPenalizacionesPagadas();

            EstadisticasCarrito estadisticas = EstadisticasCarrito.builder()
                    .totalCarritos(total)
                    .prestados(prestados)
                    .penalizados(penalizados)
                    .disponibles(disponibles)
                    .prestamosActivos(prestamosActivos)
                    .totalRecaudado(totalRecaudado != null ? totalRecaudado : BigDecimal.ZERO)
                    .build();

            return ResponseEntity.ok(DashboardCarritosDTO.builder()
                    .estadisticas(estadisticas)
                    .build());
        } catch (Exception ex) {
            log.error("Error al calcular estadísticas de carritos: {}", ex.getMessage());
            EstadisticasCarrito vacio = EstadisticasCarrito.builder()
                    .totalCarritos(carritoCargaRepository.count())
                    .prestados(0)
                    .penalizados(0)
                    .disponibles(0)
                    .prestamosActivos(0)
                    .totalRecaudado(BigDecimal.ZERO)
                    .build();
            return ResponseEntity.ok(DashboardCarritosDTO.builder().estadisticas(vacio).build());
        }
    }
}
