package com.condosaas.api.module.vehiculo.service.impl;

import com.condosaas.api.module.vehiculo.dto.*;
import com.condosaas.api.module.vehiculo.model.*;
import com.condosaas.api.module.vehiculo.repository.VehiculoRepository;
import com.condosaas.api.module.vehiculo.service.VehiculoService;
import com.condosaas.api.module.usuario.model.Usuario;
import com.condosaas.api.module.usuario.repository.UsuarioRepository;
import com.condosaas.api.security.CurrentUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final CurrentUser currentUser;

    @Override
    public VehiculoResponseDTO create(VehiculoRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Vehiculo entity = Vehiculo.builder()
                .placa(dto.getPlaca())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .color(dto.getColor())
                .estado(dto.getEstado())
                .usuario(usuario)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public VehiculoResponseDTO getById(Long id) {
        Vehiculo entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehículo no encontrado"));

        return mapToDTO(entity);
    }

    @Override
    public VehiculoResponseDTO getByPlaca(String placa) {
        Vehiculo entity = repository.findByPlaca(placa)
                .orElseThrow(() -> new EntityNotFoundException("Vehículo con placa " + placa + " no encontrado"));

        return mapToDTO(entity);
    }

    @Override
    public List<VehiculoResponseDTO> getAll(Long usuarioId) {

        List<Vehiculo> lista;

        if (usuarioId != null) {
            lista = repository.findByUsuarioId(usuarioId);
        } else if (currentUser.isScoped()) {
            // Admin de condominio: solo los vehículos de SU condominio.
            lista = repository.findByCondominioIdWithUsuario(currentUser.condominioId());
        } else {
            lista = repository.findAllWithUsuario();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public VehiculoResponseDTO update(Long id, VehiculoRequestDTO dto) {

        Vehiculo entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehículo no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        entity.setPlaca(dto.getPlaca());
        entity.setMarca(dto.getMarca());
        entity.setModelo(dto.getModelo());
        entity.setColor(dto.getColor());
        entity.setEstado(dto.getEstado());
        entity.setUsuario(usuario);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Vehículo no encontrado");
        }
        repository.deleteById(id);
    }

    private VehiculoResponseDTO mapToDTO(Vehiculo entity) {
        var usuario = entity.getUsuario();
        var apartamento = usuario != null ? usuario.getApartamento() : null;
        var piso = apartamento != null ? apartamento.getPiso() : null;
        var torre = piso != null ? piso.getTorre() : null;
        var condominio = torre != null ? torre.getCondominio() : null;

        // Tipo de ocupante: el del dueño (residente); si no tiene dueño, el del propio
        // vehículo (carros de VISITANTE registrados en portería).
        String tipoOcupante = null;
        if (usuario != null && usuario.getTipoOcupante() != null) {
            tipoOcupante = usuario.getTipoOcupante().name();
        } else if (entity.getTipoOcupante() != null) {
            tipoOcupante = entity.getTipoOcupante().name();
        }

        return VehiculoResponseDTO.builder()
                .id(entity.getId())
                .placa(entity.getPlaca())
                .marca(entity.getMarca())
                .modelo(entity.getModelo())
                .color(entity.getColor())
                .estado(entity.getEstado())
                .usuarioId(usuario != null ? usuario.getId() : null)
                .usuarioNombre(usuario != null ? usuario.getNombres() + " " + usuario.getApellidos() : "Visitante")
                .apartamentoId(apartamento != null ? apartamento.getId() : null)
                .unidad(apartamento != null ? apartamento.getNumero() : null)
                .tipoOcupante(tipoOcupante)
                .pisoNumero(piso != null ? piso.getNumero() : null)
                .torreNombre(torre != null ? torre.getNombre() : null)
                .condominioNombre(condominio != null ? condominio.getNombre() : null)
                .build();
    }
}