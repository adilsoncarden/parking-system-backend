package com.condosaas.api.module.apartamento.dto;

import com.condosaas.api.module.enums.EstadoApartamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApartamentoRequest {
    @NotBlank
    private String numeroApartamento;
    private String propietario;
    private EstadoApartamento estado;
    @NotNull
    private Long idPiso;
}
