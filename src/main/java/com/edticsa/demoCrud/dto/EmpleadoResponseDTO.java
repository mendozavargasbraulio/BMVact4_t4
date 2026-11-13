package com.edticsa.demoCrud.dto;

public record EmpleadoResponseDTO(
    Long id,
    String nombre,
    String apellido,
    String puesto,
    Long departamentoId,
    String departamentoNombre
) {}