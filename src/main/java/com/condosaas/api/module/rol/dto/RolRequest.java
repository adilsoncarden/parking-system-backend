package com.condosaas.api.module.rol.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolRequest {
    @NotBlank
    private String name;
}
