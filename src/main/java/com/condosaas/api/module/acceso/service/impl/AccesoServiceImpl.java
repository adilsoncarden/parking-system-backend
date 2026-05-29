package com.condosaas.api.module.acceso.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.enums.TipoRegistro;
import com.condosaas.api.module.acceso.dto.*;
import com.condosaas.api.module.acceso.model.AccesoVehicular;
import com.condosaas.api.module.acceso.model.EntradaSalida;
import com.condosaas.api.module.acceso.repository.AccesoVehicularRepository;
import com.condosaas.api.module.acceso.repository.EntradaSalidaRepository;
import com.condosaas.api.module.acceso.service.AccesoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AccesoServiceImpl implements AccesoService {
    private final EntradaSalidaRepository entradaSalidaRepository;
    private final AccesoVehicularRepository accesoVehicularRepository;
    private final CondominioRepository condominioRepository;

    public AccesoServiceImpl(EntradaSalidaRepository entradaSalidaRepository,
            AccesoVehicularRepository accesoVehicularRepository, CondominioRepository condominioRepository) {
        this.entradaSalidaRepository = entradaSalidaRepository;
        this.accesoVehicularRepository = accesoVehicularRepository;
        this.condominioRepository = condominioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntradaSalidaResponse> findAllEntradasSalidas(Long condominioId) {
        var list = condominioId == null ? entradaSalidaRepository.findAll()
                : entradaSalidaRepository.findByCondominioId(condominioId);
        return list.stream().map(this::toEntradaSalidaResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EntradaSalidaResponse findEntradaSalidaById(Long id) {
        return toEntradaSalidaResponse(getEntradaSalida(id));
    }

    @Override
    public EntradaSalidaResponse createEntradaSalida(EntradaSalidaRequest request) {
        EntradaSalida entity = new EntradaSalida();
        entity.setNombre(request.getNombre());
        entity.setCondominio(getCondominio(request.getIdCondominio()));
        return toEntradaSalidaResponse(entradaSalidaRepository.save(entity));
    }

    @Override
    public EntradaSalidaResponse updateEntradaSalida(Long id, EntradaSalidaRequest request) {
        EntradaSalida entity = getEntradaSalida(id);
        entity.setNombre(request.getNombre());
        entity.setCondominio(getCondominio(request.getIdCondominio()));
        return toEntradaSalidaResponse(entradaSalidaRepository.save(entity));
    }

    @Override
    public void deleteEntradaSalida(Long id) {
        entradaSalidaRepository.delete(getEntradaSalida(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccesoVehicularResponse> findAllMovimientos(Long condominioId, String placa) {
        if (placa != null) {
            return accesoVehicularRepository.findByPlaca(placa).stream().map(this::toMovimientoResponse).toList();
        }
        var list = condominioId == null ? accesoVehicularRepository.findAll()
                : accesoVehicularRepository.findByCondominioId(condominioId);
        return list.stream().map(this::toMovimientoResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AccesoVehicularResponse findMovimientoById(Long id) {
        return toMovimientoResponse(getMovimiento(id));
    }

    @Override
    public AccesoVehicularResponse registrarEntrada(AccesoVehicularRequest request) {
        return toMovimientoResponse(saveMovimiento(request, TipoRegistro.ENTRADA));
    }

    @Override
    public AccesoVehicularResponse registrarSalida(AccesoVehicularRequest request) {
        return toMovimientoResponse(saveMovimiento(request, TipoRegistro.SALIDA));
    }

    private AccesoVehicular saveMovimiento(AccesoVehicularRequest request, TipoRegistro tipo) {
        AccesoVehicular entity = new AccesoVehicular();
        entity.setPlaca(request.getPlaca());
        entity.setObservaciones(request.getObservaciones());
        entity.setHora(LocalDateTime.now());
        entity.setTipoMovimiento(tipo);
        entity.setCondominio(getCondominio(request.getIdCondominio()));
        entity.setPuntoAcceso(getEntradaSalida(request.getIdPuntoAcceso()));
        return accesoVehicularRepository.save(entity);
    }

    private EntradaSalida getEntradaSalida(Long id) {
        return entradaSalidaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EntradaSalida no encontrada: " + id));
    }

    private AccesoVehicular getMovimiento(Long id) {
        return accesoVehicularRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccesoVehicular no encontrado: " + id));
    }

    private Condominio getCondominio(Long id) {
        return condominioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Condominio no encontrado: " + id));
    }

    private EntradaSalidaResponse toEntradaSalidaResponse(EntradaSalida entity) {
        return new EntradaSalidaResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getCondominio() != null ? entity.getCondominio().getId() : null);
    }

    private AccesoVehicularResponse toMovimientoResponse(AccesoVehicular entity) {
        return new AccesoVehicularResponse(
                entity.getId(),
                entity.getHora(),
                entity.getObservaciones(),
                entity.getPlaca(),
                entity.getTipoMovimiento(),
                entity.getCondominio() != null ? entity.getCondominio().getId() : null,
                entity.getPuntoAcceso() != null ? entity.getPuntoAcceso().getId() : null);
    }
}
