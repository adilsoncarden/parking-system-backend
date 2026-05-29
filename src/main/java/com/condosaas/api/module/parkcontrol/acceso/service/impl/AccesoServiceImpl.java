package com.condosaas.api.module.parkcontrol.acceso.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.enums.EstadoOcupacion;
import com.condosaas.api.module.parkcontrol.pase_invitado.model.PaseInvitado;
import com.condosaas.api.module.parkcontrol.pase_invitado.repository.PaseInvitadoRepository;
import com.condosaas.api.module.parkcontrol.plaza.model.Plaza;
import com.condosaas.api.module.parkcontrol.plaza.repository.PlazaRepository;
import com.condosaas.api.module.parkcontrol.vehiculo.model.Vehiculo;
import com.condosaas.api.module.parkcontrol.vehiculo.repository.VehiculoRepository;
import com.condosaas.api.module.parkcontrol.acceso.dto.AccesoRequest;
import com.condosaas.api.module.parkcontrol.acceso.dto.AccesoResponse;
import com.condosaas.api.module.parkcontrol.acceso.model.Acceso;
import com.condosaas.api.module.parkcontrol.acceso.repository.AccesoRepository;
import com.condosaas.api.module.parkcontrol.acceso.service.AccesoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service("accesoParkControlServiceImpl")
@Transactional
public class AccesoServiceImpl implements AccesoService {
    private final AccesoRepository repository;
    private final VehiculoRepository vehiculoRepository;
    private final PlazaRepository plazaRepository;
    private final PaseInvitadoRepository paseInvitadoRepository;

    public AccesoServiceImpl(AccesoRepository repository, VehiculoRepository vehiculoRepository,
            PlazaRepository plazaRepository, PaseInvitadoRepository paseInvitadoRepository) {
        this.repository = repository;
        this.vehiculoRepository = vehiculoRepository;
        this.plazaRepository = plazaRepository;
        this.paseInvitadoRepository = paseInvitadoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccesoResponse> findAll(Long condominioId, Long idVehiculo) {
        List<Acceso> list;
        if (idVehiculo != null) {
            list = repository.findByVehiculoId(idVehiculo);
        } else if (condominioId != null) {
            list = repository.findByVehiculoCondominioId(condominioId);
        } else {
            list = repository.findAll();
        }
        return list.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AccesoResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Override
    public AccesoResponse registrarEntrada(AccesoRequest request) {
        Vehiculo vehiculo = getVehiculo(request.getIdVehiculo());
        repository.findByVehiculoIdAndHoraSalidaIsNull(vehiculo.getId())
                .ifPresent(a -> { throw new IllegalStateException("Vehiculo con acceso abierto"); });
        Acceso entity = new Acceso();
        entity.setHoraEntrada(LocalDateTime.now());
        entity.setVehiculo(vehiculo);
        if (request.getIdPlaza() != null) {
            Plaza plaza = getPlaza(request.getIdPlaza());
            if (plaza.getEstado() == EstadoOcupacion.OCUPADA) {
                throw new IllegalStateException("Plaza ocupada");
            }
            entity.setPlaza(plaza);
            plaza.setEstado(EstadoOcupacion.OCUPADA);
            plazaRepository.save(plaza);
        }
        if (request.getIdPaseInvitado() != null) {
            entity.setPaseInvitado(getPaseInvitado(request.getIdPaseInvitado()));
        }
        return toResponse(repository.save(entity));
    }

    @Override
    public AccesoResponse registrarSalida(Long id) {
        Acceso entity = get(id);
        if (entity.getHoraSalida() != null) {
            throw new IllegalStateException("Acceso ya cerrado");
        }
        entity.setHoraSalida(LocalDateTime.now());
        Plaza plaza = entity.getPlaza();
        if (plaza != null) {
            plaza.setEstado(EstadoOcupacion.LIBRE);
            plazaRepository.save(plaza);
        }
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(get(id));
    }

    private Acceso get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Acceso no encontrado: " + id));
    }

    private Vehiculo getVehiculo(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehiculo no encontrado: " + id));
    }

    private Plaza getPlaza(Long id) {
        return plazaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plaza no encontrada: " + id));
    }

    private PaseInvitado getPaseInvitado(Long id) {
        return paseInvitadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaseInvitado no encontrado: " + id));
    }

    private AccesoResponse toResponse(Acceso entity) {
        return new AccesoResponse(
                entity.getId(),
                entity.getHoraEntrada(),
                entity.getHoraSalida(),
                entity.getVehiculo() != null ? entity.getVehiculo().getId() : null,
                entity.getPlaza() != null ? entity.getPlaza().getId() : null,
                entity.getPaseInvitado() != null ? entity.getPaseInvitado().getId() : null);
    }
}
