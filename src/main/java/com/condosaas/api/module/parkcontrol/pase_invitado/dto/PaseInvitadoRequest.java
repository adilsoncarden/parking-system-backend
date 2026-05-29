package com.condosaas.api.module.parkcontrol.pase_invitado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaseInvitadoRequest {
    @NotBlank
    private String codigo;
    private String nombreVisitante;
    private String placaVisitante;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    @NotNull
    private Long idCondominio;
    private Long idApartamento;
}
