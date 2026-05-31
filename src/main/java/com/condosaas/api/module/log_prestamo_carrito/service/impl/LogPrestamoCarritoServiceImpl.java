package com.condosaas.api.module.log_prestamo_carrito.service.impl;

import com.condosaas.api.module.log_prestamo_carrito.dto.*;
import com.condosaas.api.module.log_prestamo_carrito.model.*;
import com.condosaas.api.module.log_prestamo_carrito.repository.LogPrestamoCarritoRepository;
import com.condosaas.api.module.log_prestamo_carrito.service.LogPrestamoCarritoService;
import com.condosaas.api.module.carrito_carga.model.CarritoCarga;
import com.condosaas.api.module.carrito_carga.repository.CarritoCargaRepository;
import com.condosaas.api.module.usuario.model.Usuario;
import com.condosaas.api.module.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LogPrestamoCarritoServiceImpl implements LogPrestamoCarritoService {

    private final LogPrestamoCarritoRepository repository;
    private final CarritoCargaRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public LogPrestamoCarritoResponseDTO create(LogPrestamoCarritoRequestDTO dto) {

        CarritoCarga carrito = carritoRepository.findById(dto.getCarritoId())
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        LogPrestamoCarrito entity = LogPrestamoCarrito.builder()
                .fechaPrestamo(dto.getFechaPrestamo())
                .fechaDevolucion(dto.getFechaDevolucion())
                .estado(dto.getEstado())
                .carrito(carrito)
                .usuario(usuario)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public LogPrestamoCarritoResponseDTO getById(Long id) {
        LogPrestamoCarrito entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));

        return mapToDTO(entity);
    }

    @Override
    public List<LogPrestamoCarritoResponseDTO> getAll(Long carritoId, Long usuarioId) {

        List<LogPrestamoCarrito> lista;

        if (carritoId != null) {
            lista = repository.findByCarritoId(carritoId);
        } else if (usuarioId != null) {
            lista = repository.findByUsuarioId(usuarioId);
        } else {
            lista = repository.findAll();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public LogPrestamoCarritoResponseDTO update(Long id, LogPrestamoCarritoRequestDTO dto) {

        LogPrestamoCarrito entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));

        CarritoCarga carrito = carritoRepository.findById(dto.getCarritoId())
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        entity.setFechaPrestamo(dto.getFechaPrestamo());
        entity.setFechaDevolucion(dto.getFechaDevolucion());
        entity.setEstado(dto.getEstado());
        entity.setCarrito(carrito);
        entity.setUsuario(usuario);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Préstamo no encontrado");
        }
        repository.deleteById(id);
    }

    private LogPrestamoCarritoResponseDTO mapToDTO(LogPrestamoCarrito entity) {
        return LogPrestamoCarritoResponseDTO.builder()
                .id(entity.getId())
                .fechaPrestamo(entity.getFechaPrestamo())
                .fechaDevolucion(entity.getFechaDevolucion())
                .estado(entity.getEstado())
                .carritoId(entity.getCarrito().getId())
                .codigoCarrito(entity.getCarrito().getCodigo())
                .usuarioId(entity.getUsuario().getId())
                .usuarioNombre(entity.getUsuario().getNombres() + " " + entity.getUsuario().getApellidos())
                .build();
    }
}