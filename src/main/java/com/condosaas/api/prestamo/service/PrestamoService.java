package com.condosaas.api.prestamo.service;

import com.condosaas.api.apartamento.entity.Apartamento;
import com.condosaas.api.apartamento.repository.ApartamentoRepository;
import com.condosaas.api.carrito.entity.CarritoCarga;
import com.condosaas.api.carrito.repository.CarritoCargaRepository;
import com.condosaas.api.entrada.entity.EntradaSalida;
import com.condosaas.api.entrada.repository.EntradaSalidaRepository;
import com.condosaas.api.prestamo.dto.PrestamoRequest;
import com.condosaas.api.prestamo.dto.PrestamoResponse;
import com.condosaas.api.prestamo.entity.Prestamo;
import com.condosaas.api.prestamo.repository.PrestamoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final CarritoCargaRepository carritoCargaRepository;
    private final ApartamentoRepository apartamentoRepository;
    private final EntradaSalidaRepository entradaSalidaRepository;

    private static final int TIEMPO_LIMITE_MINUTOS = 15;
    private static final double MONTO_MULTA = 5.0;

    public List<PrestamoResponse> listarTodos() {
        return prestamoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<PrestamoResponse> listarActivos() {
        return prestamoRepository.findByEstado("activo")
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<PrestamoResponse> listarMultados() {
        return prestamoRepository.findByMultadoTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<PrestamoResponse> listarPorCondominio(Long idCondominio) {
        return prestamoRepository.findByCarritoEntradaSalidaCondominioId(idCondominio)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PrestamoResponse crear(PrestamoRequest request) {
        CarritoCarga carrito = carritoCargaRepository.findById(request.getIdCarrito())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (!carrito.getDisponible()) {
            throw new RuntimeException("El carrito no está disponible");
        }

        Apartamento apartamento = apartamentoRepository.findById(request.getIdApartamento())
                .orElseThrow(() -> new RuntimeException("Apartamento no encontrado"));

        EntradaSalida entrada = entradaSalidaRepository.findById(request.getIdEntradaSalida())
                .orElseThrow(() -> new RuntimeException("Entrada/Salida no encontrada"));

        Prestamo prestamo = new Prestamo();
        prestamo.setCarrito(carrito);
        prestamo.setApartamento(apartamento);
        prestamo.setEntradaSalida(entrada);
        prestamo.setHoraInicio(LocalDateTime.now());
        prestamo.setEstado("activo");
        prestamo.setMultado(false);
        prestamo.setMontoMulta(0.0);

        carrito.setDisponible(false);
        carritoCargaRepository.save(carrito);

        return toResponse(prestamoRepository.save(prestamo));
    }

    public PrestamoResponse devolver(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        LocalDateTime horaFin = LocalDateTime.now();
        long minutos = java.time.Duration.between(prestamo.getHoraInicio(), horaFin).toMinutes();

        prestamo.setHoraFin(horaFin);
        prestamo.setEstado("devuelto");

        if (minutos > TIEMPO_LIMITE_MINUTOS) {
            prestamo.setMultado(true);
            prestamo.setMontoMulta(MONTO_MULTA);
        }

        prestamo.getCarrito().setDisponible(true);
        carritoCargaRepository.save(prestamo.getCarrito());

        return toResponse(prestamoRepository.save(prestamo));
    }

    public PrestamoResponse aplicarMulta(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        prestamo.setMultado(true);
        prestamo.setMontoMulta(MONTO_MULTA);
        return toResponse(prestamoRepository.save(prestamo));
    }

    private PrestamoResponse toResponse(Prestamo p) {
        PrestamoResponse response = new PrestamoResponse();
        response.setId(p.getId());
        response.setIdCarrito(p.getCarrito().getId());
        response.setNombreCarrito(p.getCarrito().getNombre());
        response.setIdApartamento(p.getApartamento().getId());
        response.setNumeroApartamento(p.getApartamento().getNumeroApartamento());
        response.setPropietario(p.getApartamento().getPropietario());
        response.setIdEntradaSalida(p.getEntradaSalida().getId());
        response.setNombreEntrada(p.getEntradaSalida().getNombre());
        response.setHoraInicio(p.getHoraInicio());
        response.setHoraFin(p.getHoraFin());
        response.setEstado(p.getEstado());
        response.setMultado(p.getMultado());
        response.setMontoMulta(p.getMontoMulta());
        return response;
    }
}