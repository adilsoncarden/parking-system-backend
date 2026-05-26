package com.condosaas.api.carrito.service;

import com.condosaas.api.carrito.dto.CarritoCargaRequest;
import com.condosaas.api.carrito.dto.CarritoCargaResponse;
import com.condosaas.api.carrito.entity.CarritoCarga;
import com.condosaas.api.carrito.repository.CarritoCargaRepository;
import com.condosaas.api.entrada.entity.EntradaSalida;
import com.condosaas.api.entrada.repository.EntradaSalidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarritoCargaService {

    private final CarritoCargaRepository carritoCargaRepository;
    private final EntradaSalidaRepository entradaSalidaRepository;

    public List<CarritoCargaResponse> listarTodos() {
        return carritoCargaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<CarritoCargaResponse> listarPorEntrada(Long idEntradaSalida) {
        return carritoCargaRepository.findByEntradaSalidaId(idEntradaSalida)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<CarritoCargaResponse> listarPorCondominio(Long idCondominio) {
        return carritoCargaRepository.findByEntradaSalidaCondominioId(idCondominio)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CarritoCargaResponse obtenerPorId(Long id) {
        CarritoCarga carrito = carritoCargaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        return toResponse(carrito);
    }

    public CarritoCargaResponse crear(CarritoCargaRequest request) {
        EntradaSalida entrada = entradaSalidaRepository.findById(request.getIdEntradaSalida())
                .orElseThrow(() -> new RuntimeException("Entrada/Salida no encontrada"));
        CarritoCarga carrito = new CarritoCarga();
        carrito.setNombre(request.getNombre());
        carrito.setDisponible(true);
        carrito.setEntradaSalida(entrada);
        return toResponse(carritoCargaRepository.save(carrito));
    }

    public CarritoCargaResponse actualizar(Long id, CarritoCargaRequest request) {
        CarritoCarga carrito = carritoCargaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        EntradaSalida entrada = entradaSalidaRepository.findById(request.getIdEntradaSalida())
                .orElseThrow(() -> new RuntimeException("Entrada/Salida no encontrada"));
        carrito.setNombre(request.getNombre());
        carrito.setEntradaSalida(entrada);
        return toResponse(carritoCargaRepository.save(carrito));
    }

    public void eliminar(Long id) {
        carritoCargaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        carritoCargaRepository.deleteById(id);
    }

    public CarritoCargaResponse cambiarDisponibilidad(Long id, Boolean disponible) {
        CarritoCarga carrito = carritoCargaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        carrito.setDisponible(disponible);
        return toResponse(carritoCargaRepository.save(carrito));
    }

    private CarritoCargaResponse toResponse(CarritoCarga c) {
        CarritoCargaResponse response = new CarritoCargaResponse();
        response.setId(c.getId());
        response.setNombre(c.getNombre());
        response.setDisponible(c.getDisponible());
        response.setIdEntradaSalida(c.getEntradaSalida().getId());
        response.setNombreEntrada(c.getEntradaSalida().getNombre());
        response.setIdCondominio(c.getEntradaSalida().getCondominio().getId());
        response.setNombreCondominio(c.getEntradaSalida().getCondominio().getNombre());
        return response;
    }
}