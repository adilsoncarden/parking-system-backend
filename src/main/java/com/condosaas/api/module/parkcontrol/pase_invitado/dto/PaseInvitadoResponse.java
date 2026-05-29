package com.condosaas.api.module.parkcontrol.pase_invitado.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaseInvitadoResponse {
    private Long id;
    private Boolean activo;
    private String codigo;
    private String nombreVisitante;
    private String placaVisitante;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Long idCondominio;
    private Long idApartamento;
}
