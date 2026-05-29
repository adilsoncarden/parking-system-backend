package com.condosaas.api.module.piso.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PisoRequest {
    @NotNull
    private Integer numeroPiso;
    @NotNull
    private Long idTorre;
}
