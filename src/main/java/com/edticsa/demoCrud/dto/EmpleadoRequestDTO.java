package com.edticsa.demoCrud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmpleadoRequestDTO(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,

    @NotBlank(message = "El apellido es obligatorio")
    String apellido,

    @NotBlank(message = "El puesto es obligatorio")
    String puesto,

    @NotNull(message = "El ID del departamento es obligatorio")
    Long departamentoId
) {}