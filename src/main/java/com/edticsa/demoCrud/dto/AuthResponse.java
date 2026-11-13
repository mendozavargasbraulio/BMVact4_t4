package com.edticsa.demoCrud.dto;

public record AuthResponse(
    String token,
    String email,
    String nombre
) {}