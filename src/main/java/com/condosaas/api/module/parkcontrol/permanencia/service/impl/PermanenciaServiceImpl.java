package com.condosaas.api.module.parkcontrol.permanencia.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.parkcontrol.acceso.model.Acceso;
import com.condosaas.api.module.parkcontrol.acceso.repository.AccesoRepository;
import com.condosaas.api.module.parkcontrol.permanencia.dto.PermanenciaRequest;
import com.condosaas.api.module.parkcontrol.permanencia.dto.PermanenciaResponse;
import com.condosaas.api.module.parkcontrol.permanencia.model.Permanencia;
import com.condosaas.api.module.parkcontrol.permanencia.repository.PermanenciaRepository;
import com.condosaas.api.module.parkcontrol.permanencia.service.PermanenciaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PermanenciaServiceImpl implements PermanenciaService {
    private final PermanenciaRepository repository;
    private final AccesoRepository accesoRepository;

    public PermanenciaServiceImpl(PermanenciaRepository repository, AccesoRepository accesoRepository) {
        this.repository = repository;
        this.accesoRepository = accesoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermanenciaResponse> findAll(Long condominioId) {
        var list = condominioId == null ? repository.findAll()
                : repository.findByAccesoVehiculoCondominioId(condominioId);
        return list.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PermanenciaResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Override
    public PermanenciaResponse calcular(PermanenciaRequest request) {
        Acceso acceso = accesoRepository.findById(request.getIdAcceso())
                .orElseThrow(() -> new ResourceNotFoundException("Acceso no encontrado: " + request.getIdAcceso()));
        LocalDateTime fin = acceso.getHoraSalida() != null ? acceso.getHoraSalida() : LocalDateTime.now();
        long minutos = Duration.between(acceso.getHoraEntrada(), fin).toMinutes();
        double tarifa = request.getTarifaPorHora() != null ? request.getTarifaPorHora() : 2.0;
        double monto = (minutos / 60.0) * tarifa;
        Permanencia entity = repository.findByAccesoId(acceso.getId()).orElse(new Permanencia());
        entity.setAcceso(acceso);
        entity.setMinutos(minutos);
        entity.setMonto(monto);
        entity.setCalculadoAt(LocalDateTime.now());
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(get(id));
    }

    private Permanencia get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permanencia no encontrada: " + id));
    }

    private PermanenciaResponse toResponse(Permanencia entity) {
        return new PermanenciaResponse(
                entity.getId(),
                entity.getCalculadoAt(),
                entity.getMinutos(),
                entity.getMonto(),
                entity.getAcceso() != null ? entity.getAcceso().getId() : null);
    }
}
