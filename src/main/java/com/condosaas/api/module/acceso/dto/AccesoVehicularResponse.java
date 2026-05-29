package com.condosaas.api.module.acceso.dto;

import com.condosaas.api.module.enums.TipoRegistro;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccesoVehicularResponse {
    private Long id;
    private LocalDateTime hora;
    private String observaciones;
    private String placa;
    private TipoRegistro tipoMovimiento;
    private Long idCondominio;
    private Long idPuntoAcceso;
}
