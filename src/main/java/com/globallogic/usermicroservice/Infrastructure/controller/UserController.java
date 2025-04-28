package com.globallogic.usermicroservice.Infrastructure.controller;

import com.globallogic.usermicroservice.Infrastructure.common.configuration.JwtUtil;
import com.globallogic.usermicroservice.application.service.impl.UserService;
import com.globallogic.usermicroservice.domain.dto.UserLoginResponseDto;
import com.globallogic.usermicroservice.domain.dto.UserRequestDto;
import com.globallogic.usermicroservice.domain.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Crea un usuario nuevo persistido en la base de datos H2, encripta la contraseña, y devuelve un JWT token de acceso."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "El correo ya existe en el sistema")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDto> signUp(@Valid @RequestBody UserRequestDto request) {
        UserResponseDto response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Login de usuario",
            description = "Valida el token JWT del usuario, actualiza su último acceso y genera un nuevo token.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = @Content(schema = @Schema(implementation = UserLoginResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido o expirado")
    })
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestHeader("Authorization") String authorizationHeader) {
        // Extraemos solo el token, quitando "Bearer "
        String token = authorizationHeader.replace("Bearer ", "").trim();

        UserLoginResponseDto response = userService.login(token);

        return ResponseEntity.ok(response);
    }


}
