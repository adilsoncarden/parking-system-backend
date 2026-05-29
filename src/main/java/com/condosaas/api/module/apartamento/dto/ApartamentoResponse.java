package com.condosaas.api.module.apartamento.dto;

import com.condosaas.api.module.enums.EstadoApartamento;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApartamentoResponse {
    private Long id;
    private String numeroApartamento;
    private String propietario;
    private EstadoApartamento estado;
    private Long idPiso;
    private LocalDateTime createdAt;
}
