package com.condosaas.api.module.piso.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PisoResponse {
    private Long id;
    private Integer numeroPiso;
    private Long idTorre;
}
