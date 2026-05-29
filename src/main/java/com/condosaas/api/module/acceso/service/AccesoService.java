package com.condosaas.api.module.acceso.service;

import com.condosaas.api.module.acceso.dto.*;
import java.util.List;

public interface AccesoService {
    List<EntradaSalidaResponse> findAllEntradasSalidas(Long condominioId);

    EntradaSalidaResponse findEntradaSalidaById(Long id);

    EntradaSalidaResponse createEntradaSalida(EntradaSalidaRequest request);

    EntradaSalidaResponse updateEntradaSalida(Long id, EntradaSalidaRequest request);

    void deleteEntradaSalida(Long id);

    List<AccesoVehicularResponse> findAllMovimientos(Long condominioId, String placa);

    AccesoVehicularResponse findMovimientoById(Long id);

    AccesoVehicularResponse registrarEntrada(AccesoVehicularRequest request);

    AccesoVehicularResponse registrarSalida(AccesoVehicularRequest request);
}
