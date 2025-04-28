package com.globallogic.usermicroservice.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private UUID id;               // ID del usuario (UUID generado)
    private LocalDateTime created; // Fecha de creación
    private LocalDateTime lastLogin; // Último ingreso
    private String token;          // Token de acceso (JWT)
    private boolean isActive;      // Si el usuario está activo
}